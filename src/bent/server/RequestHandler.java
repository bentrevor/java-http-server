package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RequestHandler implements IRequestHandler {
    private IResponseWriter writer;
    private IResponseBuilder builder;
    private IRequestReader reader;
    private ISocket connection;

    public RequestHandler(ISocket socket,
                          IRequestReader requestReader, 
                          IResponseBuilder responseBuilder,
                          IResponseWriter responseWriter) throws IOException {
        connection = socket;
        reader = requestReader;
        builder = responseBuilder;
        writer = responseWriter;
        setReaderInputStream();
        setWriterOutputStream();
    }

    public void run() {
        try {
            String inputFromSocket = reader.readFromSocket();
            HttpRequest request = new HttpRequest(inputFromSocket);
            HttpResponse response = builder.buildResponse(request);
            writer.send(response);
            connection.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void handleRequest() {
        run();
    }

    public void setReaderInputStream() throws IOException {
        reader.setInputStream(connection.getInputStream());
    }

    public void setWriterOutputStream() throws IOException {
        writer.setOutputStream(connection.getOutputStream());
    }

    public IResponseWriter getWriter() {
        return writer;
    }

    public IResponseBuilder getBuilder() {
        return builder;
    }

    public IRequestReader getReader() {
        return reader;
    }

    public ISocket getSocket() {
        return connection;
    }
}
