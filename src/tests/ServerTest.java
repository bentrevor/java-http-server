package tests;

import bent.server.sockets.MockServerSocket;
import bent.server.Server;
import bent.server.sockets.MockSocket;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockRequestHandler;

import java.io.*;

import static junit.framework.Assert.*;

@RunWith(JUnit4.class)
public class ServerTest {
    Server myServer = null;
    MockServerSocket fakeServerSocket = null;
    OutputStream fakeOutputStream = null;
    MockSocket fakeClientConnection = null;
    MockRequestHandler fakeRequestHandler = null;

    @Before
    public void setUp() throws IOException {
        fakeServerSocket = new MockServerSocket();
        fakeRequestHandler = new MockRequestHandler();

        myServer = new Server(fakeServerSocket, fakeRequestHandler);
        setUpFakeIO();
    }

    @Test
    public void itHandlesMultipleConnectionRequests() {
        fakeServerSocket.maxAccepts = 3;

        myServer.start();

        assertEquals(3, fakeServerSocket.acceptCallCount);
    }

    @Test
    public void itWritesToAnOpenedConnection() throws IOException {
        myServer.response = "hello there";

        myServer.start();

        assertEquals("hello there", fakeOutputStream.toString());
    }

    @Test
    public void itClosesTheClientConnectionAfterResponding() {
        fakeServerSocket.maxAccepts = 3;

        myServer.start();

        assertEquals(3, fakeClientConnection.closeCallCount);
    }

    @Test
    public void itTellsARequestHandlerToHandleRequest() {
        fakeServerSocket.maxAccepts = 3;

        myServer.start();

        assertEquals(3, fakeRequestHandler.handleRequestCount);
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
