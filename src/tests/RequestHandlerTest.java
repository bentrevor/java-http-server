package tests;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.RequestHandler;
import bent.server.responses.RootResponse;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockRequestReader;
import tests.mocks.MockResponseBuilder;
import tests.mocks.MockResponseWriter;
import tests.mocks.MockSocket;

import java.io.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class RequestHandlerTest {
    private MockResponseWriter fakeResponseWriter;
    private MockRequestReader fakeRequestReader;
    private MockResponseBuilder fakeResponseBuilder;
    private MockSocket fakeSocket;
    private RequestHandler handler;
    private InputStream in;

    @Before
    public void setUp() throws IOException {
        fakeResponseWriter = new MockResponseWriter();
        fakeResponseBuilder = new MockResponseBuilder();
        fakeRequestReader = new MockRequestReader();
        fakeSocket = new MockSocket();

        handler = new RequestHandler(fakeSocket, fakeRequestReader, fakeResponseBuilder, fakeResponseWriter);
    }

    @Test
    public void itTellsARequestReaderToReadARequest() throws IOException {
        makeThreeRequests();

        assertThat(fakeRequestReader.readFromSocketCallCount, is(equalTo(3)));
    }

    @Test
    public void itTellsAResponseWriterToSendAResponse() throws IOException {
        makeThreeRequests();

        assertThat(fakeResponseWriter.sendCallCount, is(equalTo(3)));
    }

    @Ignore
    public void itGivesTheBuiltResponseToTheResponseWriter() throws IOException {
        HttpResponse fakeResponse = new RootResponse();
        fakeResponse.setStatusLine("HTTP/1.1 200 OK");

        fakeResponseBuilder.setBuiltResponse(fakeResponse);

        String responseToSend = fakeResponseWriter.sendArgument.toString();

        setInputStreamContent("GET /peanuts HTTP/1.1\r\n\r\n");
        handler.run();

        assertThat(responseToSend, containsString("HTTP/1.1 200 OK"));
    }

    @Test
    public void itSendsTheParsedRequestToTheResponseBuilder() throws IOException {
        setInputStreamContent("GET /peanuts HTTP/1.1\r\n\r\n");
        handler.run();

        HttpRequest parsedRequest = fakeResponseBuilder.buildResponseArgument;

        assertThat(parsedRequest.getMethod(), is(equalTo("GET")));
        assertThat(parsedRequest.getRequestURI(), is(equalTo("/peanuts")));
        assertThat(parsedRequest.getHttpVersion(), is(equalTo("HTTP/1.1")));
    }

    @Test
    public void itSetsTheClientConnectionForTheResponseWriter() throws IOException {
        OutputStream out = new ByteArrayOutputStream();
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
        handler.run();
        setInputStreamContent("GET /peanuts HTTP/1.1\r\n\r\n");
        handler.run();
        setInputStreamContent("GET /peanuts HTTP/1.1\r\n\r\n");
        handler.run();
    }
}
