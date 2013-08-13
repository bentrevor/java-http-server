package tests;

import bent.server.HttpRequest;
import bent.server.RequestHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockRequestReader;
import tests.mocks.MockResponseBuilder;
import tests.mocks.MockResponseWriter;

import java.io.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class RequestHandlerTest {
    private MockResponseWriter fakeResponseWriter;
    private MockRequestReader fakeRequestReader;
    private MockResponseBuilder fakeResponseBuilder;
    private RequestHandler handler;
    private InputStream in;
    private OutputStream out;

    @Before
    public void setUp() throws IOException {
        fakeResponseWriter = new MockResponseWriter();
        fakeResponseBuilder = new MockResponseBuilder();
        fakeRequestReader = new MockRequestReader();

        handler = new RequestHandler(fakeRequestReader, fakeResponseBuilder, fakeResponseWriter);
    }

    @Test
    public void itTellsARequestReaderToReadARequest() throws IOException {
        makeThreeRequests();

        assertThat(fakeRequestReader.readFromSocketCallCount, is(equalTo(3)));
    }

    @Test
    public void itTellsAResponseWriterToSendAResponse() throws IOException {
        makeThreeRequests();

        assertThat(fakeResponseWriter.respondToCallCount, is(equalTo(3)));
    }

    @Test
    public void itSendsTheParsedRequestToTheResponseBuilder() throws IOException {
        setInputStreamContent("GET /peanuts HTTP/1.1\r\n\r\n");
        handler.handleRequest();

        HttpRequest parsedRequest = fakeResponseBuilder.buildResponseArgument;

        assertThat(parsedRequest.getMethod(), is(equalTo("GET")));
        assertThat(parsedRequest.getRequestURI(), is(equalTo("/peanuts")));
        assertThat(parsedRequest.getHttpVersion(), is(equalTo("HTTP/1.1")));
    }

    @Test
    public void itSetsTheClientConnectionForTheResponseWriter() throws IOException {
        out = new ByteArrayOutputStream();
        assertThat(fakeResponseWriter.setOutputStreamCallCount, is(0));

        handler.setWriterOutputStream(out);
        handler.setWriterOutputStream(out);
        handler.setWriterOutputStream(out);

        assertThat(fakeResponseWriter.setOutputStreamCallCount, is(3));
    }

    @Test
    public void itSetsTheInputStreamForTheRequestReader() throws IOException {
        assertThat(fakeRequestReader.setInputStreamCallCount, is(0));

        handler.setReaderInputStream(in);
        handler.setReaderInputStream(in);
        handler.setReaderInputStream(in);

        assertThat(fakeRequestReader.setInputStreamCallCount, is(3));
    }

    private void setInputStreamContent(String content) {
        in = new ByteArrayInputStream(content.getBytes());
        handler.setReaderInputStream(in);
    }

    private void makeThreeRequests() throws IOException {
        setInputStreamContent("GET /peanuts HTTP/1.1\r\n\r\n");
        handler.handleRequest();
        setInputStreamContent("GET /peanuts HTTP/1.1\r\n\r\n");
        handler.handleRequest();
        setInputStreamContent("GET /peanuts HTTP/1.1\r\n\r\n");
        handler.handleRequest();
    }
}
