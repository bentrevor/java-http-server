package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;

public interface IRequestReader {
    public String readFromSocket() throws IOException;
    public void setClientConnection(ISocket socket);
}
