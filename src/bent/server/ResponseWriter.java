package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter implements IResponseWriter {
    public ISocket clientConnection = null;
    public String response = null;

    public void respondTo(HttpRequest request) throws IOException {
        response = buildResponse(request);
        sendResponse(response);
    }

    public String buildResponse(HttpRequest request) {
        StringBuilder response = new StringBuilder(request.httpVersion);
        String path = request.requestURI;

        if (path.equals("/foobar")) {
            response.append(" 404 Not Found\r\n");
            response.append("Content-Length: 0\r\n");
            response.append("Content-Type: text/plain\r\n");
            response.append("\r\n");
        } else if (path.equals("/redirect")) {
            response.append(" 302 Found\r\n");
            response.append("Content-Length: 0\r\n");
            response.append("Content-Type: text/plain\r\n");
            response.append("Location: http://localhost:5000/\r\n");
            response.append("\r\n");
        } else {
            response.append(" 200 OK\r\n");
            response.append("Content-Length: 0\r\n");
            response.append("Content-Type: text/plain\r\n");
            response.append("\r\n");
        }

        return response.toString();
    }

    public void sendResponse(String response) throws IOException {
        OutputStream outputStream = clientConnection.getOutputStream();

        outputStream.write(response.getBytes());
        outputStream.flush();
    }

    public void setClientConnection(ISocket clientConnection) {
        this.clientConnection = clientConnection;
    }
}
