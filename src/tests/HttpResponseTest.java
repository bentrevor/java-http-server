package tests;

import bent.server.HttpResponse;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class HttpResponseTest {
    @Test
    public void itHasAStatusLine() {
        HttpResponse response = new HttpResponse();
        response.setHttpVersion("HTTP/1.1");
        response.setStatusCode("200");
        response.setReasonPhrase("OK");

        assertEquals("HTTP/1.1 200 OK\r\n", response.statusLine);
    }

    @Test
    public void itImplementsToString() {
        HttpResponse response = new HttpResponse();
        response.setHttpVersion("HTTP/1.1");
        response.setStatusCode("200");
        response.setReasonPhrase("OK");
        response.setContentLength(20);

        String fullResponse = response.toString();

        assertTrue(fullResponse.contains("HTTP/1.1 200 OK\r\n"));
        assertTrue(fullResponse.contains("Content-Length: 20\r\n"));
    }
}
