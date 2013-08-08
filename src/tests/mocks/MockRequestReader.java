package tests.mocks;

import bent.server.IRequestReader;
import bent.server.sockets.ISocket;

import java.io.IOException;
import java.io.InputStream;

public class MockRequestReader implements IRequestReader {
    public int readFromSocketCallCount = 0;
    public int setClientConnectionCount = 0;
    public ISocket fakeClientConnection;

    public String readFromSocket() throws IOException {
        readFromSocketCallCount++;
        InputStream in = fakeClientConnection.getInputStream();
        byte[] buffer = new byte[1000];

        in.read(buffer, 0, in.available());

        return new String(buffer);
    }

    public void setClientConnection(ISocket socket) {
        setClientConnectionCount++;
        fakeClientConnection = socket;
    }
}
