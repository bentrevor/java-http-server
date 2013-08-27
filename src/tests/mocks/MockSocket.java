package tests.mocks;

import bent.server.sockets.ISocket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MockSocket implements ISocket {
    public InputStream fakeInputStream;
    public OutputStream fakeOutputStream;
    public int closeCallCount;
    public int getInputStreamCallCount;
    public int getOutputStreamCallCount;

    public MockSocket() {
        closeCallCount = 0;
        getInputStreamCallCount = 0;
        getOutputStreamCallCount = 0;
        fakeInputStream = new ByteArrayInputStream("mock socket input stream".getBytes());
        fakeOutputStream = new ByteArrayOutputStream();
    }

    public InputStream getInputStream() {
        getInputStreamCallCount++;
        return fakeInputStream;
    }

    public OutputStream getOutputStream() {
        getOutputStreamCallCount++;
        return fakeOutputStream;
    }

    public void close() {
        closeCallCount++;
    }
}
