package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;

public class RealRequestHandler implements IRequestHandler {
    public ISocket clientConnection = null;
    public String request = "";
    public IResponseWriter responder = null;

    public RealRequestHandler(IResponseWriter responder) {
        this.responder = responder;
    }

    public void handleRequest() throws IOException {
        InputStream inputStream = clientConnection.getInputStream();
        String currentRequest = "";

        int length = inputStream.available();
        for (int i = 0; i < length; i++) {
            currentRequest += (char) inputStream.read();
        }

        request = currentRequest;

        responder.respondTo(request);
    }

    public void setClientConnection(ISocket socket) {
        clientConnection = socket;
    }
}
