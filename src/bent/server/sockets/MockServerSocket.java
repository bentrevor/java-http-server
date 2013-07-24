package bent.server.sockets;

import java.io.IOException;

public class MockServerSocket implements IServerSocket {
    public int acceptCallCount = 0;
    public ISocket createdClientConnection = null;

    public ISocket accept() throws IOException {
        acceptCallCount++;

        if (acceptCallCount > 2) {
            throw new IOException();
        }

        return createdClientConnection;
    }

    public boolean isClosed() {
        return false;
    }
}
