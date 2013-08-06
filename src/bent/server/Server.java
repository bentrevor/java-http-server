package bent.server;

import bent.server.sockets.IServerSocket;
import bent.server.sockets.ISocket;

import java.io.IOException;

public class Server {
    public IRequestHandler requestHandler;
    public IServerSocket serverSocket;
    public ISocket clientConnection;

    public Server(IServerSocket socket, IRequestHandler handler) {
        serverSocket = socket;
        requestHandler = handler;
    }

    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                clientConnection = serverSocket.accept();
                requestHandler.setClientConnection(clientConnection);
                requestHandler.handleRequest();
                clientConnection.close();
            }
        } catch (IOException e) {
            System.out.println(e + " in Server.start()");
        }
    }
}
