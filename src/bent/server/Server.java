package bent.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public ServerSocket serverSocket;
    public Socket clientSocket;
    public PrintWriter response;
    public BufferedReader request;

    public void listenOnPort(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e + " in listenOnPort() creating serverSocket");
        }
    }
    
    public void acceptConnections() {
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println(e + " in listenOnPort() creating clientSocket");
        }
    }

    public void respondToRequests() throws IOException {
        request = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        response = new PrintWriter(clientSocket.getOutputStream(), true);
        String firstLine = request.readLine();
        String path = firstLine.split(" ")[1];
        if (path.equals("/")) {
            response.println("HTTP/1.1 200 OK");
        } else {
            response.println("HTTP/1.1 404 Not Found");
        }
    }
}
