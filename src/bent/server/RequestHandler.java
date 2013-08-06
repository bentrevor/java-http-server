package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class RequestHandler implements IRequestHandler {
    public ISocket clientConnection;
    public HttpRequest request;
    public IResponseWriter responder;
    public int position;
    public char[] buffer;

    public RequestHandler(IResponseWriter responder) {
        this.responder = responder;
    }

    public void handleRequest() throws IOException {
        buffer = new char[2048];
        String currentRequest = readFromSocket();
        request = new HttpRequest(currentRequest);
        responder.respondTo(request);
    }

    public void setClientConnection(ISocket socket) {
        clientConnection = socket;
        responder.setClientConnection(socket);
    }

    public String readFromSocket() throws IOException {
        InputStream inputStream = clientConnection.getInputStream();

        Reader in = new InputStreamReader(inputStream, "UTF-8");
        position = 0;

        while (stillReading()) {
            int byteRead = in.read();
            buffer[position++] = (char) byteRead;
        }

        return trimZerosFrom(buffer);
    }

    private String trimZerosFrom(char[] buffer) {
        return new String(buffer).trim() + "\r\n\r\n";
    }

    private boolean stillReading() {
        return !(position > 3 &&
                 '\n' == buffer[position - 1] &&
                 '\r' == buffer[position - 2] &&
                 '\n' == buffer[position - 3] &&
                 '\r' == buffer[position - 4]);
    }
}
