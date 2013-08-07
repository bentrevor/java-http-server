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

        assertThat(handler.inputFromSocket, is(equalTo("GET /peanuts HTTP/1.1\r\n\r\n")));
    }

    @Test
    public void itStopsReadingAfterConsecutiveCRLFForGETRequests() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\noops!".getBytes());
        handler.handleRequest();
        String handledRequest = handler.inputFromSocket;

        assertThat(handledRequest, containsString("GET /peanuts HTTP/1.1\r\n\r\n"));
        assertThat(handledRequest, not(containsString("oops!")));
    }

    @Test
    public void itCanExtractTheContentLength() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("PUT /form HTTP/1.1\r\nContent-Length: 12\r\n\r\nsome content".getBytes());
        handler.handleRequest();
        assertThat(handler.getContentLength(), is(equalTo(12)));
    }

    @Test
    public void itReadsTheBodyForPutRequests() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("PUT /form HTTP/1.1\r\nContent-Length: 15\r\n\r\ncontent of body".getBytes());
        handler.handleRequest();
        assertThat(handler.inputFromSocket, containsString("\r\n\r\ncontent of body"));
    }

    @Test
    public void itReadsTheBodyForPostRequests() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("POST /form HTTP/1.1\r\nContent-Length: 23\r\n\r\ncontent of post request".getBytes());
        handler.handleRequest();
        assertThat(handler.inputFromSocket, containsString("\r\n\r\ncontent of post request"));
    }

    @Test
    public void itTellsAResponseWriterToSendAResponse() throws IOException {
        handler.handleRequest();
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        handler.handleRequest();
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        handler.handleRequest();

        assertThat(fakeResponseWriter.respondToCallCount, is(equalTo(3)));
    }

    @Test
    public void itSendsTheParsedRequestToTheResponder() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        handler.handleRequest();

        HttpRequest parsedRequest = fakeResponseWriter.respondToArgument;

        assertThat(parsedRequest.requestLine, is(equalTo("GET /peanuts HTTP/1.1")));
    }
}
