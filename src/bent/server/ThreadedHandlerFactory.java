package bent.server;

import bent.server.sockets.ISocket;

public class ThreadedHandlerFactory implements IHandlerFactory {
    private IHandlerFactory handlerFactory;

    public ThreadedHandlerFactory(IHandlerFactory factory) {
        handlerFactory = factory;
    }

    public IRequestHandler makeHandler(ISocket socket) {
        return handlerFactory.makeHandler(socket);
    }
}
