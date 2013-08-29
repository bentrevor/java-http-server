package bent.server;

import bent.server.sockets.RealServerSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket socketOn5000 = new ServerSocket(5000);
        RealServerSocket serverSocket = new RealServerSocket(socketOn5000);

        String rootDirectory = System.getProperty("user.dir");
        IFileManager fileManager = new FileManager(rootDirectory);

        ExecutorService service = Executors.newCachedThreadPool();
        IExecutorService executor = new RealExecutorService(service);

        IRouter router = new CobSpecRouter(fileManager);
        HandlerFactory factory = new HandlerFactory(router);

        ThreadedHandlerFactory tFactory = new ThreadedHandlerFactory(factory, executor);

        Server myServer = new Server(serverSocket, tFactory);

        myServer.start();
    }
}
