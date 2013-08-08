package tests;

import bent.server.HttpRequest;
import bent.server.RequestHandler;
import tests.mocks.MockRequestReader;
import tests.mocks.MockResponseWriter;
import tests.mocks.MockSocket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class RequestHandlerTest {
    MockResponseWriter fakeResponseWriter;
    MockSocket fakeClientConnection;
    MockRequestReader fakeRequestReader;
    RequestHandler handler;

    @Before
    public void setUp() {
        fakeResponseWriter = new MockResponseWriter();
        fakeClientConnection = new MockSocket();
        InputStream fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        fakeClientConnection.fakeInputStream = fakeInputStream;
        fakeResponseWriter = new MockResponseWriter();
        fakeRequestReader = new MockRequestReader();
        handler = new RequestHandler(fakeRequestReader, fakeResponseWriter);
        handler.setClientConnection(fakeClientConnection);
    }

    @Test
    public void itTellsARequestReaderToReadARequest() throws IOException {
        handler.handleRequest();
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        handler.handleRequest();
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        handler.handleRequest();

        assertThat(fakeRequestReader.readFromSocketCallCount, is(equalTo(3)));
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

    @Test
    public void itSetsTheClientConnectionForTheResponseWriter() throws IOException {
        handler.setClientConnection(fakeClientConnection);
        handler.setClientConnection(fakeClientConnection);

        assertThat(fakeResponseWriter.setClientConnectionCount, is(3));
    }

    @Test
    public void itSetsTheClientConnectionForTheRequestReader() throws IOException {
        handler.setClientConnection(fakeClientConnection);
        handler.setClientConnection(fakeClientConnection);

        assertThat(fakeRequestReader.setClientConnectionCount, is(3));
    }
}
