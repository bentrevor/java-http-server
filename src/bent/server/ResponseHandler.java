package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ResponseHandler {
    public ISocket clientConnection = null;

    public ResponseHandler(ISocket clientConnection) {
        this.clientConnection = clientConnection;
    }

    public void sendResponse(String response) throws IOException {
        OutputStream outputStream = clientConnection.getOutputStream();

        outputStream.write(response.getBytes());
        outputStream.flush();
    }
}
