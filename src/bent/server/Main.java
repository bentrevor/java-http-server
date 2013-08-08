package bent.server;

import bent.server.sockets.RealServerSocket;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket socketOn5000 = new ServerSocket(5000);
        RealServerSocket serverSocket = new RealServerSocket(socketOn5000);

        ResponseWriter responder = new ResponseWriter();
        RequestReader reader = new RequestReader();
        RequestHandler handler = new RequestHandler(reader, responder);

        Server myServer = new Server(serverSocket, handler);

        myServer.start();
    }
}
