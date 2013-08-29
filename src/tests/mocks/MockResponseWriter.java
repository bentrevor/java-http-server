package tests.mocks;

import bent.server.HttpResponse;
import bent.server.IResponseWriter;

import java.io.IOException;
import java.io.OutputStream;

public class MockResponseWriter implements IResponseWriter {
    public int sendCallCount;
    public int setOutputStreamCallCount;

    public MockResponseWriter() {
        sendCallCount = 0;
        setOutputStreamCallCount = 0;
    }

    public void send(HttpResponse response) throws IOException {
        sendCallCount++;
    }

    public void setOutputStream(OutputStream outputStream) {
        setOutputStreamCallCount++;
    }
}
