package bent.server;

public class ThreadedRequestHandler implements IRequestHandler {
    private IRequestHandler handler;
    private IExecutorService executor;

    public void run() {}

    public ThreadedRequestHandler(IRequestHandler requestHandler, IExecutorService service) {
        handler = requestHandler;
        executor = service;
    }

    public void handleRequest() {
        executor.execute(handler);
    }
}
