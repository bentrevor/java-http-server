package bent.server.sockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ISocket {
    public InputStream getInputStream() throws IOException;
    public OutputStream getOutputStream() throws IOException;

    public void close() throws IOException;
}
