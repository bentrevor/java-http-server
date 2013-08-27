package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;

public interface IHandlerFactory {
    public IRequestHandler makeHandler(ISocket socket) throws IOException;
}
