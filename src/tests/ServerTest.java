package tests;

import bent.server.Server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static junit.framework.Assert.*;

@RunWith(JUnit4.class)
public class ServerTest {
    @Test
    public void itCanBeCreated() {
        Server myServer = new Server();
        assertNotNull(myServer);
    }

    @Test
    public void itOpensAServerSocketOnPort5000() throws IOException {
        Server myServer = new Server();
        myServer.openSocket(5000);
        assertNotNull(myServer.serverSocket);
        assertEquals(5000, myServer.serverSocket.getLocalPort());
    }
}
