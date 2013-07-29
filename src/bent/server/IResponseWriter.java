package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;

public interface IResponseWriter {
    public void respondTo(String request) throws IOException;
    public void setClientConnection(ISocket socket);
}
