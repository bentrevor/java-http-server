package tests;

import bent.server.IRequestReader;
import bent.server.RequestReader;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockRequestReader;
import tests.mocks.MockSocket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class RequestReaderTest {
    MockSocket fakeClientConnection;
    MockRequestReader fakeRequestReader;
    RequestReader reader;

    @Before
    public void setUp() {
        fakeClientConnection = new MockSocket();
        InputStream fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\n".getBytes());
        fakeClientConnection.fakeInputStream = fakeInputStream;

        reader = new RequestReader();
        reader.setClientConnection(fakeClientConnection);
    }

    @Test
    public void itReadsRequestsFromItsClientConnection() throws IOException {
        assertThat(reader.readFromSocket(), containsString("GET /peanuts HTTP/1.1\r\n\r\n"));
    }
}
