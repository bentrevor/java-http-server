package tests;

import bent.server.HttpRequest;
import bent.server.RequestHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockResponseWriter;
import tests.mocks.MockSocket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RequestHandlerTest {
    MockResponseWriter fakeResponseWriter = null;
    MockSocket fakeClientConnection = null;
    RequestHandler handler = null;

    @Before
    public void setUp() {
        fakeResponseWriter = new MockResponseWriter();
        fakeClientConnection = new MockSocket();
        InputStream fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        fakeClientConnection.fakeInputStream = fakeInputStream;
        fakeResponseWriter = new MockResponseWriter();
        handler = new RequestHandler(fakeResponseWriter);
        handler.setClientConnection(fakeClientConnection);
    }

    @Test
    public void itReadsRequestsFromItsClientConnection() throws IOException {
        handler.handleRequest();

        HttpRequest parsedRequest = new HttpRequest("GET /peanuts HTTP/1.1\r\n\r\n");

        assertEquals(parsedRequest.method, handler.request.method);
        assertEquals(parsedRequest.httpVersion, handler.request.httpVersion);
        assertEquals(parsedRequest.requestURI, handler.request.requestURI);
    }

    @Test
    public void itReadsUntilConsecutiveCRLF() throws IOException {
        handler.buffer = new char[2048];
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("1 2 3\r\n\r\noops!".getBytes());

        assertEquals("1 2 3\r\n\r\n", handler.readFromSocket());
    }

    @Test
    public void itTellsAResponseWriterToSendAResponse() throws IOException {
        handler.handleRequest();
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        handler.handleRequest();
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        handler.handleRequest();

        assertEquals(3, fakeResponseWriter.respondToCallCount);
    }

    @Test
    public void itSendsTheParsedRequestToTheResponder() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        handler.handleRequest();

        HttpRequest parsedRequest = fakeResponseWriter.respondToArgument;

        assertEquals("GET", parsedRequest.method);
    }
}
