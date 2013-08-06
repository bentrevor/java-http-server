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
        response.setStatusLine("HTTP/1.1 200 OK");

        assertTrue(response.statusLine.contains("HTTP/1.1 200 OK"));
    }

    @Test
    public void itImplementsToString() {
        HttpResponse response = new HttpResponse();
        response.setContentLength(20);
        response.setStatusLine("HTTP/1.1 200 OK");
        response.setLocation("http://localhost:5000/");

        String fullResponse = response.toString();

        assertTrue(fullResponse.contains("HTTP/1.1 200 OK\r\n"));
        assertTrue(fullResponse.contains("Content-Length: 20\r\n"));
        assertTrue(fullResponse.contains("Location: http://localhost:5000/\r\n"));
    }

    @Test
    public void itOnlyAppendsHeadersThatHaveBeenSet() {
        HttpResponse response = new HttpResponse();
        response.setStatusLine("HTTP/1.1 200 OK");

        String fullResponse = response.toString();

        assertTrue(fullResponse.contains("HTTP/1.1 200 OK\r\n"));
        assertFalse(fullResponse.contains("Content-Length: "));
        assertFalse(fullResponse.contains("Location: "));
    }
}
