package bent.server.sockets;

import java.net.Socket;

public class RealSocket implements ISocket {
    Socket socket = null;

    public RealSocket(Socket concreteSocket) {
        socket = concreteSocket;
    }
}
