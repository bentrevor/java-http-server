package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;

public class RequestHandler implements IRequestHandler {
    private IResponseWriter responder;
    private IRequestReader reader;

    public RequestHandler(IRequestReader reader, IResponseWriter responder) {
        this.responder = responder;
        this.reader = reader;
    }

    public void handleRequest() throws IOException {
        String inputFromSocket = reader.readFromSocket();
        HttpRequest request = new HttpRequest(inputFromSocket);
        responder.respondTo(request);
    }

    public void setClientConnection(ISocket socket) throws IOException {
        responder.setOutputStream(socket.getOutputStream());
        reader.setInputStream(socket.getInputStream());
    }
}
