package tests.mocks;

import bent.server.IExecutorService;

public class MockExecutorService implements IExecutorService {
    public int executeCallCount;
    public Runnable executeArgument;

    public MockExecutorService() {
        executeCallCount = 0;
        executeArgument = null;
    }

    public void execute(Runnable runnable) {
        executeCallCount++;
        executeArgument = runnable;
    }
}
