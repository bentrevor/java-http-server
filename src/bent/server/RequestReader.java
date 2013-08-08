package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class RequestReader implements IRequestReader {
    public ISocket clientConnection;

    public RequestReader() {
    }

    public String readFromSocket() throws IOException {
        InputStream inputStream = clientConnection.getInputStream();
        Reader in = new InputStreamReader(inputStream, "UTF-8");
        int position = 0;

        char[] buffer = new char[2048];
        int byteRead = 0;

        while((byteRead = in.read()) != -1) {
            buffer[position++] = (char) byteRead;
        }

        return new String(buffer);
    }

    public void setClientConnection(ISocket socket) {
        clientConnection = socket;
    }
}

