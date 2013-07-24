package tests;

import static junit.framework.Assert.*;

import bent.server.RequestHandler;
import bent.server.sockets.MockSocket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RunWith(JUnit4.class)
public class RequestHandlerTest {
    @Test
    public void itReadsRequestsFromItsClientConnection() throws IOException {
        MockSocket fakeClientConnection = new MockSocket();
        InputStream fakeInputStream = new ByteArrayInputStream("hello".getBytes());
        fakeClientConnection.fakeInputStream = fakeInputStream;

        RequestHandler handler = new RequestHandler(fakeClientConnection);

        assertEquals("hello", handler.readRequest());
    }
}
