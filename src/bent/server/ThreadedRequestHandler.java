package bent.server;

import java.io.InputStream;
import java.io.OutputStream;

public class ThreadedRequestHandler implements IRequestHandler {
    private IRequestHandler handler;
    private IExecutorService executor;

    public ThreadedRequestHandler(IRequestHandler requestHandler, IExecutorService service) {
        handler = requestHandler;
        executor = service;
    }

    public void run() {}

    public void handleRequest() {
        executor.execute(handler);
    }

    public void setReaderInputStream(InputStream in) {}

    public void setWriterOutputStream(OutputStream out) {}
}
