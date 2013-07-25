package tests.mocks;

import bent.server.IResponseWriter;

import java.io.IOException;

public class MockResponseWriter implements IResponseWriter {
    public int respondToCallCount = 0;

    public void respondTo(String response) throws IOException {
        respondToCallCount++;
    }
}
