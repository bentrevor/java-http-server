package tests.mocks;

import bent.server.IRequestHandler;

import java.io.InputStream;
import java.io.OutputStream;

public class MockRequestHandler implements IRequestHandler {
    public int runCallCount;
    public int handleRequestCallCount;
    public int setReaderInputStreamCallCount;
    public int setWriterOutputStreamCallCount;
    public InputStream setReaderInputStreamArgument;
    public OutputStream setWriterOutputStreamArgument;

    public MockRequestHandler() {
        runCallCount = 0;
        handleRequestCallCount = 0;
        setReaderInputStreamCallCount = 0;
        setWriterOutputStreamCallCount = 0;
        setReaderInputStreamArgument = null;
        setWriterOutputStreamArgument = null;
    }

    public void run() {
        runCallCount++;
    }

    public void handleRequest() {
        handleRequestCallCount++;
    }

    public void setReaderInputStream() {
        setReaderInputStreamCallCount++;
    }

    public void setWriterOutputStream() {
        setWriterOutputStreamCallCount++;
    }
}
