package bent.server;

public class ThreadedRequestHandler implements IRequestHandler {
    private IRequestHandler handler;
    private IExecutorService executor;

    public ThreadedRequestHandler(IRequestHandler requestHandler, IExecutorService service) {
        handler = requestHandler;
        executor = service;
    }

    public void run() {
        executor.execute(handler);
    }
}
