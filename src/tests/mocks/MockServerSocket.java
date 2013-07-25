package bent.server.sockets;

import java.io.IOException;

public class MockServerSocket implements IServerSocket {
    public int acceptCallCount = 0;
    public int maxAccepts = 1;
    public ISocket createdClientConnection = null;
    public boolean isClosed = false;

    public ISocket accept() throws IOException {
        acceptCallCount++;

        if (acceptCallCount >= maxAccepts) {
            isClosed = true;
        }

        return createdClientConnection;
    }

    public boolean isClosed() {
        return isClosed;
    }
}
