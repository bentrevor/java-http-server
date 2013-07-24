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
        InputStream inputStream = clientConnection.getInputStream();

        int length = inputStream.available();
        for (int i = 0; i < length; i++) {
            request += (char) inputStream.read();
        }
    }

    public void sendResponse(String response) throws IOException {
        this.lastResponse = response;
        OutputStream outputStream = clientConnection.getOutputStream();

        outputStream.write(response.getBytes(), 0, response.getBytes().length);
    }
}
