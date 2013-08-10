package tests.mocks;

import bent.server.IRequestHandler;
import bent.server.sockets.ISocket;

import java.io.IOException;

public class MockRequestHandler implements IRequestHandler {
    public int handleRequestCount;
    public int setClientConnectionCount;

    public MockRequestHandler() {
        handleRequestCount = 0;
        setClientConnectionCount = 0;
    }

    public void handleRequest() throws IOException {
        handleRequestCount++;
    }

    public void setClientConnection(ISocket socket) {
        setClientConnectionCount++;
    }
}
