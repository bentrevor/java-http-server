package bent.server.sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RealSocket implements ISocket {
    Socket socket = null;

    public RealSocket(Socket concreteSocket) {
        socket = concreteSocket;
    }

    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }
}
