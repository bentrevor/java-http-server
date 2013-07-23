package bent.server.sockets;

import java.io.IOException;

public interface IServerSocket {
    public ISocket accept() throws IOException;
    public boolean isClosed();
}
