package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

public class RequestHandler implements IRequestHandler {
    public ISocket clientConnection = null;
    public Hashtable<String, String> request = null;
    public IResponseWriter responder = null;

    public RequestHandler(IResponseWriter responder) {
        this.responder = responder;
    }

    public void handleRequest() throws IOException {
        InputStream inputStream = clientConnection.getInputStream();
        String currentRequest = "";

        int length = inputStream.available();
        for (int i = 0; i < length; i++) {
            currentRequest += (char) inputStream.read();
        }

        HttpParser parser = new HttpParser();
        request = parser.parse(currentRequest);

        responder.respondTo(request);
    }

    public void setClientConnection(ISocket socket) {
        clientConnection = socket;
        responder.setClientConnection(socket);
    }
}
