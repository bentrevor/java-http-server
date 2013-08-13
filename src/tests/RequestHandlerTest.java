package tests;

import bent.server.HttpRequest;
import bent.server.RequestHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockRequestReader;
import tests.mocks.MockResponseWriter;

import java.io.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class RequestHandlerTest {
    private MockResponseWriter fakeResponseWriter;
    private MockRequestReader fakeRequestReader;
    private RequestHandler handler;
    private InputStream fakeInputStream;
    private OutputStream fakeOutputStream;

    @Before
    public void setUp() throws IOException {
        fakeResponseWriter = new MockResponseWriter();
        fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        fakeOutputStream = new ByteArrayOutputStream();
        fakeResponseWriter = new MockResponseWriter();
        fakeRequestReader = new MockRequestReader();
        handler = new RequestHandler(fakeRequestReader, fakeResponseWriter);
        handler.setReaderInputStream(fakeInputStream);
        handler.setWriterOutputStream(fakeOutputStream);
    }

    @Test
    public void itTellsARequestReaderToReadARequest() throws IOException {
        handler.handleRequest();
        fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        handler.handleRequest();
        fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        handler.handleRequest();

        assertThat(fakeRequestReader.readFromSocketCallCount, is(equalTo(3)));
    }

    @Test
    public void itTellsAResponseWriterToSendAResponse() throws IOException {
        handler.handleRequest();
        fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        handler.handleRequest();
        fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        handler.handleRequest();

        assertThat(fakeResponseWriter.respondToCallCount, is(equalTo(3)));
    }

    @Test
    public void itSendsTheParsedRequestToTheResponder() throws IOException {
        fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        handler.setReaderInputStream(fakeInputStream);
        handler.handleRequest();

        HttpRequest parsedRequest = fakeResponseWriter.respondToArgument;

        assertThat(parsedRequest.getMethod(), is(equalTo("GET")));
        assertThat(parsedRequest.getRequestURI(), is(equalTo("/peanuts")));
        assertThat(parsedRequest.getHttpVersion(), is(equalTo("HTTP/1.1")));
    }

    @Test
    public void itSetsTheClientConnectionForTheResponseWriter() throws IOException {
        handler.setWriterOutputStream(fakeOutputStream);
        handler.setWriterOutputStream(fakeOutputStream);

        assertThat(fakeResponseWriter.setOutputStreamCallCount, is(3));
    }

    @Test
    public void itSetsTheInputStreamForTheRequestReader() throws IOException {
        handler.setReaderInputStream(fakeInputStream);
        handler.setReaderInputStream(fakeInputStream);

        assertThat(fakeRequestReader.setInputStreamCallCount, is(3));
    }
}
