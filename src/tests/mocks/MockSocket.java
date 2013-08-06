package tests.mocks;

import bent.server.sockets.ISocket;

import java.io.InputStream;
import java.io.OutputStream;

public class MockSocket implements ISocket {
    public InputStream fakeInputStream;
    public OutputStream fakeOutputStream;
    public int closeCallCount;

    public InputStream getInputStream() {
        return fakeInputStream;
    }

    public OutputStream getOutputStream() {
        return fakeOutputStream;
    }

    public void close() {
        closeCallCount++;
    }
}
