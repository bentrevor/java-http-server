package bent.server.sockets;

import java.io.InputStream;
import java.io.OutputStream;

public class MockSocket implements ISocket {
    public InputStream fakeInputStream = null;
    public OutputStream fakeOutputStream = null;

    public InputStream getInputStream() {
        return fakeInputStream;
    }

    public OutputStream getOutputStream() {
        return fakeOutputStream;
    }

    public void close() {
        fakeInputStream = null;
        fakeOutputStream = null;
    }
}
