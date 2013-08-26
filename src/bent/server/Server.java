package bent.server;

import bent.server.sockets.IServerSocket;
import bent.server.sockets.ISocket;

import java.io.IOException;

public class Server {
    private IServerSocket serverSocket;
    private IHandlerFactory handlerFactory;
    private IExecutorService executor;

    public Server(IServerSocket socket, IHandlerFactory factory, IExecutorService executorService) {
        executor = executorService;
        handlerFactory = factory;
        serverSocket = socket;
    }

    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                IRequestHandler requestHandler = handlerFactory.makeHandler();
                ISocket clientConnection = serverSocket.accept();
                requestHandler.setReaderInputStream(clientConnection.getInputStream());
                requestHandler.setWriterOutputStream(clientConnection.getOutputStream());
                executor.execute(requestHandler);
                clientConnection.close();
            }
        } catch (IOException e) {
            System.out.println(e + " in Server.start()");
        }
    }
}
