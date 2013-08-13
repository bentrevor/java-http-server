package tests.mocks;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.IResponseWriter;

import java.io.IOException;
import java.io.OutputStream;

public class MockResponseWriter implements IResponseWriter {
    public int sendCallCount;
    public HttpResponse sendArgument;
    public int setOutputStreamCallCount;

    public MockResponseWriter() {
        sendCallCount = 0;
        sendArgument = null;
        setOutputStreamCallCount = 0;
    }

    public void send(HttpResponse response) throws IOException {
        sendCallCount++;
        sendArgument = response;
    }

    public void setOutputStream(OutputStream outputStream) {
        setOutputStreamCallCount++;
    }
}
