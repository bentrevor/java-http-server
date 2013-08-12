package tests.mocks;

import bent.server.IRequestReader;

import java.io.IOException;
import java.io.InputStream;

public class MockRequestReader implements IRequestReader {
    public int readFromSocketCallCount;
    public int setInputStreamCallCount;
    private InputStream inputStream;

    public MockRequestReader() {
        readFromSocketCallCount = 0;
        setInputStreamCallCount = 0;
    }

    public String readFromSocket() throws IOException {
        readFromSocketCallCount++;

        byte[] buffer = new byte[1000];

        inputStream.read(buffer, 0, inputStream.available());

        return new String(buffer);
    }

    public void setInputStream(InputStream inputStream) {
        setInputStreamCallCount++;
        this.inputStream = inputStream;
    }
}
