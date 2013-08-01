package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.util.Hashtable;

public interface IResponseWriter {
    public void respondTo(HttpRequest request) throws IOException;
    public void setClientConnection(ISocket socket);
}
