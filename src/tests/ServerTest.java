package tests;

import bent.server.Server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static junit.framework.Assert.*;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.*;

@RunWith(JUnit4.class)
public class ServerTest {
    Server myServer = null;
    Socket clientSocket = null;

    @Before
    public void setUp() {
        myServer = new Server();
    }

    @After
    public void tearDown() throws IOException, NullPointerException {
        try {
            myServer.serverSocket.close();
            clientSocket.close();
        } catch (NullPointerException e) {
            System.out.println(e + " in tearDown()");
        }
    }

    @Test
    public void itCanBeCreated() {
        assertNotNull(myServer);
    }

    @Test
    public void itOpensAServerSocketOnAGivenPort() throws IOException {
        myServer.listenOnPort(5000);

        assertNotNull(myServer.serverSocket);
        assertEquals(5000, myServer.serverSocket.getLocalPort());
    }

    @Test
    public void itCanConnectToAClientSocket() throws IOException {
        myServer.listenOnPort(5000);
        clientSocket = new Socket();
        clientSocket.connect(myServer.serverSocket.getLocalSocketAddress());

        assertEquals(5000, clientSocket.getPort());
    }

    @Test
    public void itCanReadFromTheOpenedSocket() throws IOException {
        connectClientToServerOnPort(5000);


        PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader serverInput = new BufferedReader(new InputStreamReader(myServer.clientSocket.getInputStream()));

        clientOutput.println("hello\n");

        assertEquals(5000, myServer.clientSocket.getLocalPort());
        assertEquals("hello", serverInput.readLine());

        clientOutput.close();
        serverInput.close();
        myServer.clientSocket.close();
        clientSocket.close();
    }

    @Test
    public void itCanRespondToGETRequests() throws IOException {
        connectClientToServerOnPort(5000);

        PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        clientOutput.println("GET / HTTP/1.1");
        myServer.respondToRequests();
        String response = clientInput.readLine();

        assertThat(response, containsString("200"));

        clientOutput.close();
        clientInput.close();
        myServer.clientSocket.close();
        clientSocket.close();
    }

    private void connectClientToServerOnPort(int port) throws IOException {
        myServer.listenOnPort(port);

        clientSocket = new Socket();
        clientSocket.connect(myServer.serverSocket.getLocalSocketAddress());

        myServer.acceptConnections();
    }
}
