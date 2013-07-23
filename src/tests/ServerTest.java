package tests;

import bent.server.sockets.MockServerSocket;
import bent.server.Server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static junit.framework.Assert.*;

@RunWith(JUnit4.class)
public class ServerTest {
    Server myServer = null;
    MockServerSocket fakeServerSocket = null;

    @Before
    public void setUp() throws IOException {
        fakeServerSocket = new MockServerSocket();
        myServer = new Server(fakeServerSocket);
    }

    @Test
    public void itHandlesMultipleConnectionRequests() {
        myServer.start();
        assertTrue(fakeServerSocket.acceptCallCount > 0);
    }
}
