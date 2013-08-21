package tests.mocks;

import bent.server.IHandlerFactory;

public class MockHandlerFactory implements IHandlerFactory {
    public int makeHandlerCount;
    public MockRequestHandler createdHandler;

    public MockHandlerFactory() {
        makeHandlerCount = 0;
        createdHandler = new MockRequestHandler();
    }

    public MockRequestHandler makeHandler() {
        makeHandlerCount++;
        return createdHandler;
    }
}
