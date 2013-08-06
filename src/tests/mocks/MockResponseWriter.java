package tests.mocks;

import bent.server.HttpRequest;
import bent.server.IResponseWriter;
import bent.server.sockets.ISocket;

import java.io.IOException;

public class MockResponseWriter implements IResponseWriter {
    public int respondToCallCount = 0;
    public HttpRequest respondToArgument = null;

    public void respondTo(HttpRequest request) throws IOException {
        respondToCallCount++;
        respondToArgument = request;
    }

    public void setClientConnection(ISocket socket) {
    }
}
