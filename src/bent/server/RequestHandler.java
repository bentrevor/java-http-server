package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RequestHandler implements IRequestHandler {
    private IResponseWriter writer;
    private IResponseBuilder builder;
    private IRequestReader reader;

    public RequestHandler(IRequestReader reader, IResponseBuilder builder, IResponseWriter writer) {
        this.writer = writer;
        this.builder = builder;
        this.reader = reader;
    }

    public void handleRequest() throws IOException {
        String inputFromSocket = reader.readFromSocket();
        HttpRequest request = new HttpRequest(inputFromSocket);
        builder.buildResponse(request);
        writer.respondTo(request);
    }

    public void setReaderInputStream(InputStream in) {
        reader.setInputStream(in);
    }

    public void setWriterOutputStream(OutputStream out) {
        writer.setOutputStream(out);
    }
}
