package tests;

import static junit.framework.Assert.*;

import bent.server.HttpRequest;
import bent.server.RequestHandler;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockResponseWriter;
import tests.mocks.MockSocket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

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

    @Ignore
    public void itReadsUntilConsecutiveCRLF() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("1 2 3\r\n\r\n".getBytes());
        handler.handleRequest();

        assertEquals(0, fakeResponseWriter.respondToCallCount);
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

    @Test
    public void itOnlyParsesRequestsWithConsecutiveNewlines() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("invalid".getBytes());
        handler.handleRequest();

        assertEquals(0, fakeResponseWriter.respondToCallCount);
    }

    @Test
    public void itOnlyParsesTestsWithValidRequestLine() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("invalid\r\n\r\n".getBytes());
        handler.handleRequest();

        assertEquals(0, fakeResponseWriter.respondToCallCount);
    }
}
