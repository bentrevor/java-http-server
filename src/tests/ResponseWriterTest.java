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
}
