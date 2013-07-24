package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;

public class RequestHandler {
    public ISocket clientConnection = null;

    public RequestHandler(ISocket clientConnection) {
        this.clientConnection = clientConnection;
    }

    public String readRequest() throws IOException {
        InputStream inputStream = clientConnection.getInputStream();
        String request = "";

        int length = inputStream.available();
        for (int i = 0; i < length; i++) {
            request += (char) inputStream.read();
        }

        return request;
    }
}
