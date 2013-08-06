package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter implements IResponseWriter {
    public ISocket clientConnection;
    public String response;

    public void respondTo(HttpRequest request) throws IOException {
        response = buildResponse(request);
        sendResponse(response);
    }

    public String buildResponse(HttpRequest request) {
        HttpResponse response = new ResponseBuilder().buildResponse(request);

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
