package bent.server;

import bent.server.sockets.IServerSocket;
import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Server {
    public IServerSocket serverSocket = null;
    public ISocket clientConnection = null;
    public String request = "";
    public String lastResponse = "";

    public Server(IServerSocket socket) {
        serverSocket = socket;
    }

    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                clientConnection = serverSocket.accept();
            }
        } catch (IOException e) {
            System.out.println(e + " in Server.start()");
        }
    }

    public void readRequest() throws IOException {
        RequestHandler handler = new RequestHandler(clientConnection);
        request = handler.readRequest();
    }

    public void sendResponse(String response) throws IOException {
        this.lastResponse = response;
        OutputStream outputStream = clientConnection.getOutputStream();

        outputStream.write(response.getBytes(), 0, response.getBytes().length);
    }
}
