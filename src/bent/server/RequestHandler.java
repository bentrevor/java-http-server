package bent.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RequestHandler implements IRequestHandler {
    private IResponseWriter writer;
    private IResponseBuilder builder;
    private IRequestReader reader;

    public RequestHandler(IRequestReader requestReader, IResponseBuilder responseBuilder, IResponseWriter responseWriter) {
        reader = requestReader;
        builder = responseBuilder;
        writer = responseWriter;
    }

    public void handleRequest() throws IOException {
        String inputFromSocket = reader.readFromSocket();
        HttpRequest request = new HttpRequest(inputFromSocket);
        HttpResponse response = builder.buildResponse(request);
        writer.send(response);
    }

    public void setReaderInputStream(InputStream in) {
        reader.setInputStream(in);
    }

    public void setWriterOutputStream(OutputStream out) {
        writer.setOutputStream(out);
    }
}
