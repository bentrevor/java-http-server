package tests.mocks;

import bent.server.IRequestHandler;
import bent.server.sockets.ISocket;

import java.io.IOException;

public class MockRequestHandler implements IRequestHandler {
    public ISocket clientConnection;
    public int handleRequestCount;

    public void handleRequest() throws IOException {
        handleRequestCount++;
    }

    public void setClientConnection(ISocket socket) {
        clientConnection = socket;
    }
}
