package tests;

import static junit.framework.Assert.*;

import bent.server.RealRequestHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockSocket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RunWith(JUnit4.class)
public class RequestHandlerTest {

    @Test
    public void itReadsRequestsFromItsClientConnection() throws IOException {
        MockSocket fakeClientConnection = new MockSocket();
        InputStream fakeInputStream = new ByteArrayInputStream("peanuts".getBytes());
        fakeClientConnection.fakeInputStream = fakeInputStream;

        RealRequestHandler handler = new RealRequestHandler();
        handler.setClientConnection(fakeClientConnection);
        handler.handleRequest();

        assertEquals("peanuts", handler.request);
    }
}
