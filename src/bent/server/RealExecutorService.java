package bent.server;

import java.util.concurrent.ExecutorService;

public class RealExecutorService implements IExecutorService {
    private ExecutorService executor;

    public RealExecutorService(ExecutorService service) {
        executor = service;
    }

    public void execute(Runnable command) {
        executor.execute(command);
    }
}
