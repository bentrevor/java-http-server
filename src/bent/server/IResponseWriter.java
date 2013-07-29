package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;
import java.util.Hashtable;

public interface IResponseWriter {
    public void respondTo(Hashtable<String, String> request) throws IOException;
    public void setClientConnection(ISocket socket);
}
