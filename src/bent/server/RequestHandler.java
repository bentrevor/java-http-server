package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class RequestHandler implements IRequestHandler {
    public ISocket clientConnection;
    public HttpRequest request;
    public IResponseWriter responder;
    public IRequestReader reader;
    public int position;
    public char[] buffer;
    public String inputFromSocket;

    public RequestHandler(IRequestReader reader, IResponseWriter responder) {
        this.responder = responder;
        this.reader = reader;
    }

    public void handleRequest() throws IOException {
        buffer = new char[2048];
        inputFromSocket = reader.readFromSocket();
        request = new HttpRequest(inputFromSocket);
        responder.respondTo(request);
    }

    public String readFromSocket() throws IOException {
        InputStream inputStream = clientConnection.getInputStream();
        Reader in = new InputStreamReader(inputStream, "UTF-8");
        position = 0;

        readHeaders(in);

        if (bodyShouldBeRead()) {
            readBody(in);
        }

        return trimZerosFrom(buffer);
    }

    public void setClientConnection(ISocket socket) {
        clientConnection = socket;
        responder.setClientConnection(socket);
    }

    public int extractContentLength() {
        return Integer.parseInt(new String(buffer).split("Content-Length")[1].split("\r\n")[0].split(":")[1].trim());
    }

    private void readHeaders(Reader in) throws IOException {
        while (stillReadingHeaders()) {
            int byteRead = in.read();
            buffer[position++] = (char) byteRead;
        }
    }

    private void readBody(Reader in) throws IOException {
        int contentLength = extractContentLength();
        for (int i = 0; i < contentLength; i++) {
            int byteRead = in.read();
            buffer[position++] = (char) byteRead;
        }
    }

    private boolean putOrPostRequest() {
        return buffer[0] == 'P';
    }

    private boolean bodyShouldBeRead() {
        return putOrPostRequest() && contentLengthProvided();
    }

    private boolean contentLengthProvided() {
        String headers = new String(buffer).trim();
        return headers.contains("Content-Length");
    }

    private String trimZerosFrom(char[] buffer) {
        return new String(buffer).trim() + "\r\n\r\n";
    }

    private boolean stillReadingHeaders() {
        return !(position > 3 &&
                '\n' == buffer[position - 1] &&
                '\r' == buffer[position - 2] &&
                '\n' == buffer[position - 3] &&
                '\r' == buffer[position - 4]);
    }
}
