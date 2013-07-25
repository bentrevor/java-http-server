package bent.server;

import bent.server.sockets.RealServerSocket;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        RealRequestHandler handler = new RealRequestHandler();
        Server myServer = new Server(new RealServerSocket(serverSocket), handler);
        myServer.start();
    }
}
