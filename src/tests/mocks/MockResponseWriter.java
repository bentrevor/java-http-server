package tests.mocks;

import bent.server.IResponseWriter;
import bent.server.sockets.ISocket;

import java.io.IOException;
import java.util.Hashtable;

public class MockResponseWriter implements IResponseWriter {
    public int respondToCallCount = 0;
    public Hashtable<String, String> respondToArgument = null;

    public void respondTo(Hashtable<String, String> request) throws IOException {
        respondToCallCount++;
        respondToArgument = request;
    }

    public void setClientConnection(ISocket socket) {
    }
}
