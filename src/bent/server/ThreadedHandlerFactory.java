package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;

public class ThreadedHandlerFactory implements IHandlerFactory {
    private IHandlerFactory handlerFactory;
    private IExecutorService service;

    public ThreadedHandlerFactory(IHandlerFactory factory, IExecutorService executor) {
        handlerFactory = factory;
        service = executor;
    }

    public IRequestHandler makeHandler(ISocket socket) throws IOException {
        IRequestHandler handler = handlerFactory.makeHandler(socket);

        return new ThreadedRequestHandler(handler, service);
    }
}
