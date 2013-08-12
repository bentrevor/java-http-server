package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    public void setReaderInputStream(InputStream in) {
        reader.setInputStream(in);
    }

    public void setWriterOutputStream(OutputStream out) {
        responder.setOutputStream(out);
    }
}
