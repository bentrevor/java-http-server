package tests.mocks;

import bent.server.IRequestReader;

public class MockRequestReader implements IRequestReader {
    public int readFromSocketCallCount = 0;

    public String readFromSocket() {
        readFromSocketCallCount++;
        return "MockRequestReader.readFromSocket()";
    }
}
