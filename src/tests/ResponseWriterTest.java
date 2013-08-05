package tests;

import static junit.framework.Assert.*;

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
import java.util.Hashtable;

@RunWith(JUnit4.class)
public class ResponseWriterTest {
    public HttpRequest fakeGetRequest = null;

    @Before
    public void setUp() {
        fakeGetRequest = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
    }

    @Test
    public void itWritesResponsesToItsClientConnection() throws IOException {
        MockSocket fakeClientConnection = new MockSocket();
        OutputStream fakeOutputStream = new ByteArrayOutputStream();
        fakeClientConnection.fakeOutputStream = fakeOutputStream;

        ResponseWriter responder = new ResponseWriter();
        responder.setClientConnection(fakeClientConnection);

        responder.sendResponse("hi");

        assertEquals("hi", fakeOutputStream.toString());
    }

    @Test
    public void itSendsASuccessfulResponse() throws IOException {
        MockSocket fakeClientConnection = new MockSocket();
        OutputStream fakeOutputStream = new ByteArrayOutputStream();
        fakeClientConnection.fakeOutputStream = fakeOutputStream;

        ResponseWriter responder = new ResponseWriter();
        responder.setClientConnection(fakeClientConnection);

        responder.respondTo(fakeGetRequest);

        assertEquals("HTTP/1.1 200 OK\r\nContent-Length: 0\r\nContent-Type: text/plain\r\n\r\n", responder.response);
        assertEquals("HTTP/1.1 200 OK\r\nContent-Length: 0\r\nContent-Type: text/plain\r\n\r\n", fakeOutputStream.toString());
    }

    @Test
    public void itSendsA404ResponseForInvalidRoutes() throws IOException {
        MockSocket fakeClientConnection = new MockSocket();
        OutputStream fakeOutputStream = new ByteArrayOutputStream();
        fakeClientConnection.fakeOutputStream = fakeOutputStream;

        ResponseWriter responder = new ResponseWriter();
        responder.setClientConnection(fakeClientConnection);

        fakeGetRequest = new HttpRequest("GET /foobar HTTP/1.1\r\n\r\n");
        responder.respondTo(fakeGetRequest);

        assertEquals("HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\nContent-Type: text/plain\r\n\r\n", responder.response);
        assertEquals("HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\nContent-Type: text/plain\r\n\r\n", fakeOutputStream.toString());
    }

    @Test
    public void itHandlesARedirectRoute() throws IOException {
        MockSocket fakeClientConnection = new MockSocket();
        OutputStream fakeOutputStream = new ByteArrayOutputStream();
        fakeClientConnection.fakeOutputStream = fakeOutputStream;

        ResponseWriter responder = new ResponseWriter();
        responder.setClientConnection(fakeClientConnection);

        fakeGetRequest = new HttpRequest("GET /redirect HTTP/1.1\r\n\r\n");
        responder.respondTo(fakeGetRequest);

        assertEquals("HTTP/1.1 302 Found\r\nContent-Length: 0\r\nContent-Type: text/plain\r\nLocation: http://localhost:5000/\r\n\r\n", responder.response);
        assertEquals("HTTP/1.1 302 Found\r\nContent-Length: 0\r\nContent-Type: text/plain\r\nLocation: http://localhost:5000/\r\n\r\n", fakeOutputStream.toString());
    }
}
