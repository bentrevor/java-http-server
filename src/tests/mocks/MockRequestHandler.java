package tests.mocks;

import bent.server.IRequestHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MockRequestHandler implements IRequestHandler {
    public int handleRequestCallCount;
    public int setReaderInputStreamCallCount;
    public int setWriterOutputStreamCallCount;
    public InputStream setReaderInputStreamArgument;
    public OutputStream setWriterOutputStreamArgument;

    public MockRequestHandler() {
        handleRequestCallCount = 0;
        setReaderInputStreamCallCount = 0;
        setWriterOutputStreamCallCount = 0;
        setReaderInputStreamArgument = null;
        setWriterOutputStreamArgument = null;
    }

    public void handleRequest() throws IOException {
        handleRequestCallCount++;
    }

    public void setReaderInputStream(InputStream in) {
        setReaderInputStreamCallCount++;
        setReaderInputStreamArgument = in;
    }

    public void setWriterOutputStream(OutputStream out) {
        setWriterOutputStreamCallCount++;
        setWriterOutputStreamArgument = out;
    }
}
