package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

public class ResponseWriter implements IResponseWriter {
    public ISocket clientConnection = null;
    public String response = "";

    public void respondTo(HttpRequest request) throws IOException {
        String path = request.requestURI;

        if (path.equals("/foobar")) {
            response = "HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\nContent-Type: text/plain\r\n\r\n";
        } else if (path.equals("/redirect")) {
            response = "HTTP/1.1 302 Found\r\nContent-Length: 0\r\nContent-Type: text/plain\r\nLocation: http://localhost:5000/\r\n\r\n";
        } else {
            response = "HTTP/1.1 200 OK\r\nContent-Length: 0\r\nContent-Type: text/plain\r\n\r\n";
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
