package bent.server;


import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;

public interface IRequestReader {
    public String readFromSocket() throws IOException;
    public void setInputStream(InputStream stream);
}
