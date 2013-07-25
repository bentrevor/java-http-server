package tests;

import bent.server.sockets.MockServerSocket;
import bent.server.Server;
import bent.server.sockets.MockSocket;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

import static junit.framework.Assert.*;

@RunWith(JUnit4.class)
public class ServerTest {
    Server myServer = null;
    MockServerSocket fakeServerSocket = null;
    OutputStream fakeOutputStream = null;
    MockSocket fakeClientConnection = null;

    @Before
    public void setUp() throws IOException {
        fakeServerSocket = new MockServerSocket();
        myServer = new Server(fakeServerSocket);
        setUpFakeIO();
    }

    @Test
    public void itHandlesMultipleConnectionRequests() {
        fakeServerSocket.maxAccepts = 3;

        myServer.start();

        assertEquals(3, fakeServerSocket.acceptCallCount);
    }

    @Test
    public void itReadsFromOpenedClientConnection() throws IOException {
        InputStream fakeInputStream = new ByteArrayInputStream("peanuts".getBytes());
        fakeClientConnection.fakeInputStream = fakeInputStream;

        myServer.start();

        assertEquals("peanuts", myServer.request);
    }

    @Test
    public void itWritesToAnOpenedConnection() throws IOException {
        myServer.response = "hello there";

        myServer.start();

        assertEquals("hello there", fakeOutputStream.toString());
    }

    private void setUpFakeIO() {
        fakeClientConnection = new MockSocket();
        fakeOutputStream = new ByteArrayOutputStream();
        InputStream fakeInputStream = new ByteArrayInputStream("hello".getBytes());
        fakeClientConnection.fakeInputStream = fakeInputStream;
        fakeClientConnection.fakeOutputStream = fakeOutputStream;
        fakeServerSocket.createdClientConnection = fakeClientConnection;
    }
}
