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

import static junit.framework.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

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
    public void itStopsReadingAfterConsecutiveCRLFForGETRequests() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\noops!".getBytes());

        assertEquals("GET /peanuts HTTP/1.1\r\n\r\n", handler.readFromSocket());
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
    public void itCanExtractTheContentLength() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("PUT /form HTTP/1.1\r\nContent-Length: 12\r\n\r\nsome content".getBytes());
        handler.readFromSocket();
        assertThat(handler.getContentLength(), is(equalTo(12)));
    }

    @Test
    public void itReadsTheBodyForPutRequests() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("PUT /form HTTP/1.1\r\nContent-Length: 15\r\n\r\ncontent of body".getBytes());
        assertThat(handler.readFromSocket(), containsString("\r\n\r\ncontent of body"));
    }

    @Test
    public void itReadsTheBodyForPostRequests() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("POST /form HTTP/1.1\r\nContent-Length: 23\r\n\r\ncontent of post request".getBytes());
        assertThat(handler.readFromSocket(), containsString("\r\n\r\ncontent of post request"));
    }
}
