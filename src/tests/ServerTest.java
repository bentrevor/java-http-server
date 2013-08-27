package tests;

import bent.server.Server;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockHandlerFactory;
import tests.mocks.MockRequestHandler;
import tests.mocks.MockServerSocket;
import tests.mocks.MockSocket;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class ServerTest {
    private Server myServer;
    private MockServerSocket fakeServerSocket;
    private MockRequestHandler fakeRequestHandler;
    private MockHandlerFactory fakeHandlerFactory;

    @Before
    public void setUp() throws IOException {
        fakeServerSocket = new MockServerSocket();
        fakeRequestHandler = new MockRequestHandler();
        fakeHandlerFactory = new MockHandlerFactory();
        fakeHandlerFactory.createdHandler = fakeRequestHandler;

        myServer = new Server(fakeServerSocket, fakeHandlerFactory);
        setUpFakeIO();
    }

    @Test
    public void itHandlesMultipleConnectionRequests() {
        fakeServerSocket.maxAccepts = 3;

        myServer.start();

        assertThat(fakeServerSocket.acceptCallCount, is(3));
    }

    @Test
    public void itUsesAHandlerFactory() {
        fakeServerSocket.maxAccepts = 3;

        myServer.start();

        assertThat(fakeHandlerFactory.makeHandlerCount, is(3));
    }

    @Test
    public void itPassesTheCreatedConnectionToRequestHandlerFactory() {
        fakeServerSocket.maxAccepts = 1;

        myServer.start();

        assertSame(fakeHandlerFactory.makeHandlerArgument, fakeServerSocket.createdClientConnection);
    }

    @Test
    public void itTellsTheRequestHandlerToRun() {
        fakeServerSocket.maxAccepts = 3;

        myServer.start();

        assertThat(fakeRequestHandler.runCallCount, is(3));
    }

    private void setUpFakeIO() {
        MockSocket fakeClientConnection = new MockSocket();
        OutputStream fakeOutputStream = new ByteArrayOutputStream();
        InputStream fakeInputStream = new ByteArrayInputStream("hello".getBytes());
        fakeClientConnection.fakeInputStream = fakeInputStream;
        fakeClientConnection.fakeOutputStream = fakeOutputStream;
        fakeServerSocket.createdClientConnection = fakeClientConnection;
    }
}
