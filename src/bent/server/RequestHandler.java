package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class RequestHandler implements IRequestHandler {
    public ISocket clientConnection = null;
    public HttpRequest request = null;
    public IResponseWriter responder = null;

    public RequestHandler(IResponseWriter responder) {
        this.responder = responder;
    }

    public void handleRequest() throws IOException {
        String currentRequest = readFromSocket();

        if (valid(currentRequest)) {
            request = new HttpRequest(currentRequest);
            responder.respondTo(request);
        }
    }

    public void setClientConnection(ISocket socket) {
        clientConnection = socket;
        responder.setClientConnection(socket);
    }

    private String readFromSocket() throws IOException {
        InputStream inputStream = clientConnection.getInputStream();

        StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "UTF-8");

        while (true) {
            int byteRead = in.read();

            if (byteRead < 0) {
                break;
            }
            out.append((char) byteRead);
        }

        return out.toString();
    }

    private boolean valid(String request) {
        boolean validNewlines = request.indexOf("\r\n\r\n") != -1;
        boolean validSpaces = request.indexOf(" ") != request.lastIndexOf(" ");

        return validNewlines && validSpaces;
    }
}
