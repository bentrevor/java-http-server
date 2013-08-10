package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;

public interface IRequestHandler {
    public void handleRequest() throws IOException;
    public void setClientConnection(ISocket socket) throws IOException;
}
