package tests;

import bent.server.sockets.MockServerSocket;
import bent.server.Server;
import bent.server.sockets.MockSocket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

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
        assertTrue(fakeServerSocket.acceptCallCount > 1);
    }

    @Test
    public void itReadsFromOpenedClientConnection() throws IOException {
        MockSocket fakeClientConnection = new MockSocket();
        InputStream fakeInputStream = new ByteArrayInputStream("hello".getBytes());
        fakeClientConnection.fakeInputStream = fakeInputStream;
        fakeServerSocket.createdClientConnection = fakeClientConnection;

        myServer.start();
        myServer.readRequest();

        assertEquals("hello", myServer.request);
    }

    @Test
    public void itWritesToAnOpenedConnection() throws IOException {
        MockSocket fakeClientConnection = new MockSocket();
        OutputStream fakeOutputStream = new ByteArrayOutputStream();
        fakeClientConnection.fakeOutputStream = fakeOutputStream;
        fakeServerSocket.createdClientConnection = fakeClientConnection;

        myServer.start();
        myServer.sendResponse("hello");

        assertEquals("hello", myServer.lastResponse);
    }
}
