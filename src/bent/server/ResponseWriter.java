package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter implements IResponseWriter {
    public ISocket clientConnection = null;

    public void respondTo(String request) {
    }

    public void setClientConnection(ISocket clientConnection) {
        this.clientConnection = clientConnection;
    }

    public void sendResponse(String response) throws IOException {
        OutputStream outputStream = clientConnection.getOutputStream();

        outputStream.write(response.getBytes());
        outputStream.flush();
    }
}
