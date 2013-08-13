package bent.server.sockets;

import java.io.IOException;
import java.net.ServerSocket;

public class RealServerSocket implements IServerSocket {
    private ServerSocket serverSocket;

    public RealServerSocket(ServerSocket socket){
        serverSocket = socket;
    }

    public ISocket accept() throws IOException {
        return new RealSocket(serverSocket.accept());
    }

    public boolean isClosed() {
        return serverSocket.isClosed();
    }
}
