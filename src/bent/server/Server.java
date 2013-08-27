package bent.server;

import bent.server.sockets.IServerSocket;
import bent.server.sockets.ISocket;

import java.io.IOException;

public class Server {
    private IServerSocket serverSocket;
    private IHandlerFactory handlerFactory;

    public Server(IServerSocket socket, IHandlerFactory factory) {
        handlerFactory = factory;
        serverSocket = socket;
    }

    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                ISocket clientConnection = serverSocket.accept();
                IRequestHandler requestHandler = handlerFactory.makeHandler(clientConnection);
                requestHandler.run();
            }
        } catch (IOException e) {
            System.out.println(e + " in Server.start()");
        }
    }
}
