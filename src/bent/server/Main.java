package bent.server;

import bent.server.sockets.RealServerSocket;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket socketOn5000 = new ServerSocket(5000);
        RealServerSocket serverSocket = new RealServerSocket(socketOn5000);

        String rootDirectory = System.getProperty("user.dir");
        IFileSystem fileSystem = new FileSystem(rootDirectory);

        IRouter router = new CobSpecRouter(fileSystem);
        HandlerFactory factory = new HandlerFactory(router);

        Server myServer = new Server(serverSocket, factory);

        myServer.start();
    }
}
