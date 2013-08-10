package tests;

import bent.server.RequestReader;
import tests.mocks.MockSocket;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class RequestReaderTest {
    MockSocket fakeClientConnection;
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

    @Test
    public void itStopsReadingAfterConsecutiveCRLFForGETRequests() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("GET /peanuts HTTP/1.1\r\n\r\noops!".getBytes());

        String inputFromSocket = reader.readFromSocket();

        assertThat(inputFromSocket, containsString("GET /peanuts HTTP/1.1\r\n\r\n"));
        assertThat(inputFromSocket, not(containsString("oops!")));
    }

    @Test
    public void itCanExtractTheContentLength() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("PUT /form HTTP/1.1\r\nContent-Length: 15\r\n\r\ncontent of body".getBytes());

        reader.readFromSocket();
        int contentLength = reader.extractContentLength();

        assertThat(contentLength, is(equalTo(15)));
    }

    @Test
    public void itReadsTheBodyForPutRequests() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("PUT /form HTTP/1.1\r\nContent-Length: 15\r\n\r\ncontent of body".getBytes());

        String inputFromSocket = reader.readFromSocket();

        assertThat(inputFromSocket, containsString("\r\n\r\ncontent of body"));
    }

    @Test
    public void itReadsTheBodyForPostRequests() throws IOException {
        fakeClientConnection.fakeInputStream = new ByteArrayInputStream("POST /form HTTP/1.1\r\nContent-Length: 23\r\n\r\ncontent of post request".getBytes());

        String inputFromSocket = reader.readFromSocket();

        assertThat(inputFromSocket, containsString("\r\n\r\ncontent of post request"));
    }
}
