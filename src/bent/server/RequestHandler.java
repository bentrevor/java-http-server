package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class RequestHandler implements IRequestHandler {
    public HttpRequest request;
    public IResponseWriter responder;
    public IRequestReader reader;

    public RequestHandler(IRequestReader reader, IResponseWriter responder) {
        this.responder = responder;
        this.reader = reader;
    }

    public void handleRequest() throws IOException {
        String inputFromSocket = reader.readFromSocket();
        request = new HttpRequest(inputFromSocket);
        responder.respondTo(request);
    }

    public void setClientConnection(ISocket socket) {
        responder.setClientConnection(socket);
        reader.setClientConnection(socket);
    }
}
