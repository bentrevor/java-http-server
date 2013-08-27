package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;

public class ThreadedHandlerFactory implements IHandlerFactory {
    private IHandlerFactory handlerFactory;

    public ThreadedHandlerFactory(IHandlerFactory factory) {
        handlerFactory = factory;
    }

    public IRequestHandler makeHandler(ISocket socket) throws IOException {
        return handlerFactory.makeHandler(socket);
    }
}
