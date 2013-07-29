package tests.mocks;

import bent.server.IResponseWriter;
import bent.server.sockets.ISocket;

import java.io.IOException;

public class MockResponseWriter implements IResponseWriter {
    public int respondToCallCount = 0;

    public void respondTo(String response) throws IOException {
        respondToCallCount++;
    }

    public void setClientConnection(ISocket socket) {
    }
}
