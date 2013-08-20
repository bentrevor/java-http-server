package bent.server;

import bent.server.sockets.RealServerSocket;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket socketOn5000 = new ServerSocket(5000);
        RealServerSocket serverSocket = new RealServerSocket(socketOn5000);

        String rootDir = System.getProperty("user.dir") + "/public";
        IFileSystem fileSystem = new FileSystem(rootDir);
        IRouter router = new CobSpecRouter(fileSystem);

        ResponseWriter responder = new ResponseWriter();
        ResponseBuilder builder = new ResponseBuilder(router);
        RequestReader reader = new RequestReader();
        RequestHandler handler = new RequestHandler(reader, builder, responder);

        Server myServer = new Server(serverSocket, handler);

        myServer.start();
    }
}
