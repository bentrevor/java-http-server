package tests;

import bent.server.HttpRequest;
import bent.server.ResponseWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockSocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class ResponseWriterTest {
    public HttpRequest fakeGetRequest;
    public MockSocket fakeClientConnection;
    public OutputStream fakeOutputStream;
    public ResponseWriter responder;

    @Before
    public void setUp() {
        fakeClientConnection = new MockSocket();
        fakeOutputStream = new ByteArrayOutputStream();
        fakeClientConnection.fakeOutputStream = fakeOutputStream;

        responder = new ResponseWriter();
        responder.setClientConnection(fakeClientConnection);
    }

    @Test
    public void itWritesResponsesToItsClientConnection() throws IOException {
        fakeGetRequest = new HttpRequest("GET / HTTP/1.1\r\n\r\n");

        responder.sendResponse("hi");

        assertThat(fakeOutputStream.toString(), containsString("hi"));
    }

    @Test
    public void itSendsASuccessfulResponse() throws IOException {
        fakeGetRequest = new HttpRequest("GET / HTTP/1.1\r\n\r\n");

        responder.respondTo(fakeGetRequest);

        assertThat(fakeOutputStream.toString(), containsString("HTTP/1.1 200 OK\r\n"));
    }

    @Test
    public void itSendsA404ResponseForInvalidRoutes() throws IOException {
        fakeGetRequest = new HttpRequest("GET /foobar HTTP/1.1\r\n\r\n");

        responder.respondTo(fakeGetRequest);

        assertThat(fakeOutputStream.toString(), containsString("HTTP/1.1 404 Not Found\r\n"));
    }

    @Test
    public void itHandlesARedirectRoute() throws IOException {
        fakeGetRequest = new HttpRequest("GET /redirect HTTP/1.1\r\n\r\n");

        responder.respondTo(fakeGetRequest);

        assertThat(fakeOutputStream.toString(), containsString("HTTP/1.1 302 Found\r\n"));
    }
}
