package bent.server;

import bent.server.sockets.ISocket;

public interface IHandlerFactory {
    public IRequestHandler makeHandler(ISocket socket);
}
