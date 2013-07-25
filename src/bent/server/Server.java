package bent.server;

import bent.server.sockets.IServerSocket;
import bent.server.sockets.ISocket;

import java.io.IOException;

public class Server {
    public IServerSocket serverSocket = null;
    public ISocket clientConnection = null;
    public String response = "";
    public IRequestHandler requestHandler = null;

    public Server(IServerSocket socket, IRequestHandler handler) {
        serverSocket = socket;
        requestHandler = handler;
    }

    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                clientConnection = serverSocket.accept();
                requestHandler.setClientConnection(clientConnection);
                requestHandler.handleRequest();
                //response = "HTTP/1.1 200 OK\nContent-Length: 0\nContent-Type: text/plain\n\n";
                sendResponse();
                clientConnection.close();
            }
        } catch (IOException e) {
            System.out.println(e + " in Server.start()");
        }
    }

    private void sendResponse() throws IOException {
        ResponseHandler handler = new ResponseHandler(clientConnection);
        handler.sendResponse(response);
        response = "";
    }
}
