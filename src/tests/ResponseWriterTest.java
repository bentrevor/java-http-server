package tests;

import static junit.framework.Assert.*;

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
    Hashtable<String, String> fakeGetRequest = null;

    @Before
    public void setUp() {
        fakeGetRequest = new Hashtable<String, String>();
        fakeGetRequest.put("Method", "GET");
        fakeGetRequest.put("Request-URI", "/");
        fakeGetRequest.put("HTTP-Version", "HTTP/1.1");
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

        assertEquals("HTTP/1.1 200 OK\nContent-Length: 0\nContent-Type: text/plain\n\n\n\n", responder.response);
        assertEquals("HTTP/1.1 200 OK\nContent-Length: 0\nContent-Type: text/plain\n\n\n\n", fakeOutputStream.toString());
    }

    @Test
    public void itSendsA404ResponseForInvalidRoutes() throws IOException {
        MockSocket fakeClientConnection = new MockSocket();
        OutputStream fakeOutputStream = new ByteArrayOutputStream();
        fakeClientConnection.fakeOutputStream = fakeOutputStream;

        ResponseWriter responder = new ResponseWriter();
        responder.setClientConnection(fakeClientConnection);

        fakeGetRequest.put("Request-URI", "/foobar");
        responder.respondTo(fakeGetRequest);

        assertEquals("HTTP/1.1 404 Not Found\nContent-Length: 0\nContent-Type: text/plain\n\n\n\n", responder.response);
        assertEquals("HTTP/1.1 404 Not Found\nContent-Length: 0\nContent-Type: text/plain\n\n\n\n", fakeOutputStream.toString());
    }
}
