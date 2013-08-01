package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Hashtable;

public class RequestHandler implements IRequestHandler {
    public ISocket clientConnection = null;
    public Hashtable<String, String> request = null;
    public IResponseWriter responder = null;

    public RequestHandler(IResponseWriter responder) {
        this.responder = responder;
    }

    public void handleRequest() throws IOException {
        String currentRequest = readFromSocket();
        HttpParser parser = new HttpParser();

        if (valid(currentRequest)) {
            request = parser.parse(currentRequest);
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
        char[] buffer = new char[200];
        Reader in = new InputStreamReader(inputStream, "UTF-8");

        while (true) {
            int bytesRead = in.read(buffer, 0, buffer.length);
            if (bytesRead < 0) {
                break;
            }
            out.append(buffer, 0, buffer.length);
        }

        return out.toString();
    }

    private boolean valid(String request) {
        boolean validNewlines = request.indexOf("\r\n\r\n") != -1;
        boolean validSpaces = request.indexOf(" ") != request.lastIndexOf(" ");

        return validNewlines && validSpaces;
    }
}
