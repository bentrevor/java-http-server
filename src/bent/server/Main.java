package bent.server;
import bent.server.Server;

import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Server myServer = new Server();
        myServer.listenOnPort(5000);
        myServer.acceptConnections();
        myServer.respondToRequests();

        myServer.serverSocket.close();
        myServer.clientSocket.close();
    }

    public static void log(String arg) {
        System.out.println(arg);
    }
}
