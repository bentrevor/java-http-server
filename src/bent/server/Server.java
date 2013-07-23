package bent.server;

import bent.server.sockets.IServerSocket;
import bent.server.sockets.ISocket;

import java.io.IOException;

public class Server {
    public IServerSocket serverSocket = null;
    public ISocket clientConnection = null;
    public String request = "";

    public Server(IServerSocket socket) {
        serverSocket = socket;
    }

    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                clientConnection = serverSocket.accept();
            }
        } catch (IOException e) {
            System.out.println(e + " in Server.start()");
        }
    }
}
