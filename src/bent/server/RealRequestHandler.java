package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;

public class RealRequestHandler implements IRequestHandler {
    public ISocket clientConnection = null;
    public String request = "";

    public void handleRequest() throws IOException {
        InputStream inputStream = clientConnection.getInputStream();
        String currentRequest = "";

        int length = inputStream.available();
        for (int i = 0; i < length; i++) {
            currentRequest += (char) inputStream.read();
        }

        request = currentRequest;
    }

    public void setClientConnection(ISocket socket) {
        clientConnection = socket;
    }
}
