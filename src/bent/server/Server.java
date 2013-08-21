package bent.server;

import bent.server.sockets.IServerSocket;
import bent.server.sockets.ISocket;

import java.io.IOException;

public class Server {
    private IRequestHandler requestHandler;
    private IServerSocket serverSocket;
    private IHandlerFactory handlerFactory;

    public Server(IServerSocket socket, IHandlerFactory factory) {
        handlerFactory = factory;
        serverSocket = socket;
    }

    public Server(IServerSocket socket, IRequestHandler handler) {
        serverSocket = socket;
        requestHandler = handler;
    }

    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                requestHandler = handlerFactory.makeHandler();
                ISocket clientConnection = serverSocket.accept();
                requestHandler.setReaderInputStream(clientConnection.getInputStream());
                requestHandler.setWriterOutputStream(clientConnection.getOutputStream());
                requestHandler.handleRequest();
                clientConnection.close();
            }
        } catch (IOException e) {
            System.out.println(e + " in Server.start()");
        }
    }
}
