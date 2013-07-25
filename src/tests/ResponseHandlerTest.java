package tests;

import static junit.framework.Assert.*;

import bent.server.ResponseHandler;
import bent.server.sockets.MockSocket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@RunWith(JUnit4.class)
public class ResponseHandlerTest {

    @Test
    public void itWritesResponsesToItsClientConnection() throws IOException {
        MockSocket fakeClientConnection = new MockSocket();
        OutputStream fakeOutputStream = new ByteArrayOutputStream();
        fakeClientConnection.fakeOutputStream = fakeOutputStream;

        ResponseHandler handler = new ResponseHandler(fakeClientConnection);

        handler.sendResponse("hi");

        assertEquals("hi", fakeOutputStream.toString());
    }
}
