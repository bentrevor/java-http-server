package tests;

import bent.server.HttpRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class HttpRequestTest {
    HttpRequest request = null;

    @Test
    public void itExtractsHeaders() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        assertEquals("GET / HTTP/1.1", request.headers);
    }

    @Test
    public void itExtractsTheMethodFromRequestLine() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        assertEquals("GET", request.method);

        request = new HttpRequest("PUT / HTTP/1.1\r\n\r\n");
        assertEquals("PUT", request.method);
    }

    @Test
    public void itExtractsThePathFromRequestLine() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        assertEquals("/", request.requestURI);

        request = new HttpRequest("PUT /peanuts HTTP/1.1\r\n\r\n");
        assertEquals("/peanuts", request.requestURI);
    }

    @Test
    public void itExtractsTheHttpVersionFromRequestLine() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        assertEquals("HTTP/1.1", request.httpVersion);
    }
}
