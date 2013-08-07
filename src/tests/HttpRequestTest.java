package tests;

import bent.server.HttpRequest;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class HttpRequestTest {
    HttpRequest request = null;

    @Test
    public void itExtractsTheRequestLine() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        assertEquals("GET / HTTP/1.1", request.requestLine);
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

    @Test
    public void itExcludesRequestLineFromHeaders() {
        request = new HttpRequest("GET / HTTP/1.1\r\nAccept: text/plain\r\nContent-Length: 0\r\n\r\n");
        assertEquals(request.headers.length, 2);
    }

    @Test
    public void itExcludesBodyFromHeaders() {
        request = new HttpRequest("PUT / HTTP/1.1\r\n" +
                                  "Accept: text/plain\r\n" +
                                  "Content-Length: 20\r\n" +
                                  "\r\n" +
                                  "bla bla bla bla bla ");
        assertEquals(request.headers.length, 2);
    }

    @Test
    public void itExtractsHeaders() {
        request = new HttpRequest("GET / HTTP/1.1\r\n" +
                                  "Accept: text/plain\r\n" +
                                  "Content-Length: 20\r\n" +
                                  "\r\n");
        assertEquals(2, request.headers.length);
        assertEquals(20, request.contentLength);
        assertEquals("text/plain", request.accept);
    }

    @Test
    public void itCanHandleALoneRequestLine() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        assertEquals(0, request.headers.length);
    }

    @Ignore
    public void itReadsTheBodyForPutRequests() {
        request = new HttpRequest("PUT /form HTTP/1.1\r\n\r\nsome data\r\n\r\n");
        assertNotNull(request.body);
    }
}
