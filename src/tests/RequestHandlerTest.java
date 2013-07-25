package tests;

import static junit.framework.Assert.*;

import bent.server.RealRequestHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockResponseWriter;
import tests.mocks.MockSocket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RunWith(JUnit4.class)
public class RequestHandlerTest {
    MockResponseWriter fakeResponseWriter = null;
    MockSocket fakeClientConnection = null;
    RealRequestHandler handler = null;

    @Before
    public void setUp() {
        fakeResponseWriter = new MockResponseWriter();
        fakeClientConnection = new MockSocket();
        InputStream fakeInputStream = new ByteArrayInputStream("peanuts".getBytes());
        fakeClientConnection.fakeInputStream = fakeInputStream;
        fakeResponseWriter = new MockResponseWriter();
        handler = new RealRequestHandler(fakeResponseWriter);
        handler.setClientConnection(fakeClientConnection);
    }

    @Test
    public void itReadsRequestsFromItsClientConnection() throws IOException {
        handler.handleRequest();

        assertEquals("peanuts", handler.request);
    }

    @Test
    public void itTellsAResponseWriterToSendAResponse() throws IOException {
        handler.handleRequest();
        handler.handleRequest();
        handler.handleRequest();

        assertEquals(3, fakeResponseWriter.respondToCallCount);
    }
}
