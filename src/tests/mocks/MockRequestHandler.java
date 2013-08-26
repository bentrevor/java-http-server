package tests.mocks;

import bent.server.IRequestHandler;

import java.io.InputStream;
import java.io.OutputStream;

public class MockRequestHandler implements IRequestHandler {
    public int runCallCount;
    public int setReaderInputStreamCallCount;
    public int setWriterOutputStreamCallCount;
    public InputStream setReaderInputStreamArgument;
    public OutputStream setWriterOutputStreamArgument;

    public MockRequestHandler() {
        runCallCount = 0;
        setReaderInputStreamCallCount = 0;
        setWriterOutputStreamCallCount = 0;
        setReaderInputStreamArgument = null;
        setWriterOutputStreamArgument = null;
    }

    public void run() {
        runCallCount++;
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
