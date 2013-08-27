package tests.mocks;

import bent.server.IRequestHandler;

import java.io.InputStream;
import java.io.OutputStream;

public class MockRequestHandler implements IRequestHandler {
    public int runCallCount;

    public MockRequestHandler() {
        runCallCount = 0;
    }

    public void run() {
        runCallCount++;
    }
}
