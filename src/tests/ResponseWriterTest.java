package tests;

import static junit.framework.Assert.*;

import bent.server.ResponseWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockSocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@RunWith(JUnit4.class)
public class ResponseWriterTest {

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

        responder.respondTo("GET / HTTP/1.1");

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

        responder.respondTo("GET /foobar HTTP/1.1");

        assertEquals("HTTP/1.1 404 Not Found\nContent-Length: 0\nContent-Type: text/plain\n\n\n\n", responder.response);
        assertEquals("HTTP/1.1 404 Not Found\nContent-Length: 0\nContent-Type: text/plain\n\n\n\n", fakeOutputStream.toString());
    }
}
