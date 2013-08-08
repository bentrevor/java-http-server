package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class RequestReader implements IRequestReader {
    public ISocket clientConnection;
    public char[] buffer;
    public int position;

    public String readFromSocket() throws IOException {
        InputStream inputStream = clientConnection.getInputStream();
        Reader in = new InputStreamReader(inputStream, "UTF-8");
        position = 0;

        buffer = new char[2048];

        while (stillReadingHeaders()) {
            int byteRead = in.read();
            buffer[position++] = (char) byteRead;
        }

        if (bodyShouldBeRead()) {
            int contentLength = extractContentLength();
            for (int i = 0; i < contentLength; i++) {
                int byteRead = in.read();
                buffer[position++] = (char) byteRead;
            }
        }

        return new String(buffer);
    }

    public void setClientConnection(ISocket socket) {
        clientConnection = socket;
    }

    public int extractContentLength() {
        return Integer.parseInt(new String(buffer).split("Content-Length")[1].split("\r\n")[0].split(":")[1].trim());
    }

    private boolean stillReadingHeaders() {
        return !(position > 3 &&
                '\n' == buffer[position - 1] &&
                '\r' == buffer[position - 2] &&
                '\n' == buffer[position - 3] &&
                '\r' == buffer[position - 4]);
    }

    private boolean bodyShouldBeRead() {
        return putOrPostRequest() && contentLengthProvided();
    }

    private boolean putOrPostRequest() {
        return buffer[0] == 'P';
    }

    private boolean contentLengthProvided() {
        String headers = new String(buffer).trim();
        return headers.contains("Content-Length");
    }
}

