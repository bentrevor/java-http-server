package tests.mocks;

import bent.server.IExecutorService;

public class MockExecutorService implements IExecutorService {
    public int executeCallCount;

    public MockExecutorService() {
        executeCallCount = 0;
    }

    public void execute(Runnable runnable) {
        executeCallCount++;
    }
}
