package bent.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public ServerSocket serverSocket;

    public void openSocket(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }
}
