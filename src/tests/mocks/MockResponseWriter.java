package tests.mocks;

import bent.server.HttpRequest;
import bent.server.IResponseWriter;

import java.io.IOException;
import java.io.OutputStream;

public class MockResponseWriter implements IResponseWriter {
    public int respondToCallCount;
    public HttpRequest respondToArgument;
    public int setOutputStreamCallCount;

    public MockResponseWriter() {
        respondToCallCount = 0;
        respondToArgument = null;
        setOutputStreamCallCount = 0;
    }

    public void respondTo(HttpRequest request) throws IOException {
        respondToCallCount++;
        respondToArgument = request;
    }

    public void setOutputStream(OutputStream outputStream) {
        setOutputStreamCallCount++;
    }
}
