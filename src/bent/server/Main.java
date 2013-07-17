package bent.server;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Server myServer = new Server();
        myServer.listenOnPort(5000);
        myServer.acceptConnections();
        myServer.respondToRequests();

        myServer.serverSocket.close();
        myServer.clientSocket.close();
    }
}
