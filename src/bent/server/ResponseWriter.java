package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

public class ResponseWriter implements IResponseWriter {
    public ISocket clientConnection = null;
    public String response = "";

    public void respondTo(Hashtable<String, String> request) throws IOException {
        String path = request.get("Request-URI");

        if (path.equals("/foobar")) {
            response = "HTTP/1.1 404 Not Found\nContent-Length: 0\nContent-Type: text/plain\n\n\n\n";
        } else {
            response = "HTTP/1.1 200 OK\nContent-Length: 0\nContent-Type: text/plain\n\n\n\n";
        }
        sendResponse(response);
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
