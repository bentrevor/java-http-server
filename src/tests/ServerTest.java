package tests;

import bent.server.Server;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.*;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class ServerTest {
    private Server myServer;
    private MockServerSocket fakeServerSocket;
    private MockSocket fakeClientConnection;
    private MockRequestHandler fakeRequestHandler;
    private MockHandlerFactory fakeHandlerFactory;
    private MockExecutorService fakeExecutorService;

    @Before
    public void setUp() throws IOException {
        fakeServerSocket = new MockServerSocket();
        fakeRequestHandler = new MockRequestHandler();
        fakeHandlerFactory = new MockHandlerFactory();
        fakeHandlerFactory.createdHandler = fakeRequestHandler;

        fakeExecutorService = new MockExecutorService();

        myServer = new Server(fakeServerSocket, fakeHandlerFactory, fakeExecutorService);
        setUpFakeIO();
    }

    @Test
    public void itHandlesMultipleConnectionRequests() {
        fakeServerSocket.maxAccepts = 3;

        myServer.start();

        assertThat(fakeServerSocket.acceptCallCount, is(3));
    }

    @Test
    public void itClosesTheClientConnectionAfterResponding() {
        fakeServerSocket.maxAccepts = 3;

        myServer.start();

        assertThat(fakeClientConnection.closeCallCount, is(3));
    }

    @Test
    public void itTellTheRequestHandlerToSetAnInputStream() {
        fakeServerSocket.maxAccepts = 3;

        myServer.start();

        assertThat(fakeRequestHandler.setReaderInputStreamCallCount, is(3));
        assertThat(fakeRequestHandler.setReaderInputStreamArgument, is(fakeClientConnection.fakeInputStream));
    }

    @Test
    public void itTellTheRequestHandlerToSetAnOutputStream() {
        fakeServerSocket.maxAccepts = 3;

        myServer.start();

        assertThat(fakeRequestHandler.setWriterOutputStreamCallCount, is(3));
        assertThat(fakeRequestHandler.setWriterOutputStreamArgument, is(fakeClientConnection.fakeOutputStream));
    }

    @Test
    public void itUsesAHandlerFactory() {
        fakeServerSocket.maxAccepts = 3;

        myServer.start();

        assertThat(fakeHandlerFactory.makeHandlerCount, is(3));
    }

    @Test
    public void itUsesAnExecutorService() {
        fakeServerSocket.maxAccepts = 3;

        myServer.start();

        assertThat(fakeExecutorService.executeCallCount, is(3));
    }

    private void setUpFakeIO() {
        fakeClientConnection = new MockSocket();
        OutputStream fakeOutputStream = new ByteArrayOutputStream();
        InputStream fakeInputStream = new ByteArrayInputStream("hello".getBytes());
        fakeClientConnection.fakeInputStream = fakeInputStream;
        fakeClientConnection.fakeOutputStream = fakeOutputStream;
        fakeServerSocket.createdClientConnection = fakeClientConnection;
    }
}
