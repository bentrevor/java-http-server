package bent.server.sockets;

import java.io.IOException;

public class MockServerSocket implements IServerSocket {
    public int acceptCallCount = 0;
    public ISocket socketToReturn = null;

    public ISocket accept() throws IOException {
        acceptCallCount++;

        if (acceptCallCount > 2) {
            throw new IOException();
        }

        return socketToReturn;
    }

    public boolean isClosed() {
        return false;
    }
}
