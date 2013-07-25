package bent.server.sockets;

import java.io.InputStream;
import java.io.OutputStream;

public class MockSocket implements ISocket {
    public InputStream fakeInputStream = null;
    public OutputStream fakeOutputStream = null;
    public int closeCallCount = 0;

    public InputStream getInputStream() {
        return fakeInputStream;
    }

    public OutputStream getOutputStream() {
        return fakeOutputStream;
    }

    public void close() {
        closeCallCount++;
    }
}
