package tests;

import bent.server.HttpRequest;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class HttpRequestTest {
    HttpRequest request = null;

    @Test
    public void itExtractsTheRequestLine() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        assertThat(request.requestLine, is(equalTo("GET / HTTP/1.1")));
    }

    @Test
    public void itExtractsTheMethodFromRequestLine() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        assertThat(request.getMethod(), is(equalTo("GET")));

        request = new HttpRequest("PUT / HTTP/1.1\r\n\r\nbody\r\n\r\n");
        assertThat(request.getMethod(), is(equalTo("PUT")));
    }

    @Test
    public void itExtractsThePathFromRequestLine() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        assertThat(request.getRequestURI(), is(equalTo("/")));

        request = new HttpRequest("PUT /peanuts HTTP/1.1\r\n\r\nbody\r\n\r\n");
        assertThat(request.getRequestURI(), is(equalTo("/peanuts")));
    }

    @Test
    public void itExtractsTheHttpVersionFromRequestLine() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        assertThat(request.getHttpVersion(), is(equalTo("HTTP/1.1")));
    }

    @Test
    public void itExcludesRequestLineFromHeaders() {
        request = new HttpRequest("GET / HTTP/1.1\r\nAccept: text/plain\r\nContent-Length: 0\r\n\r\n");
        assertThat(request.headers.size(), is(equalTo(2)));
    }

    @Test
    public void itExcludesBodyFromHeaders() {
        request = new HttpRequest("PUT / HTTP/1.1\r\n" +
                                  "Accept: text/plain\r\n" +
                                  "Content-Length: 20\r\n" +
                                  "\r\n" +
                                  "bla bla bla bla bla ");
        assertThat(request.headers.size(), is(equalTo(2)));
    }

    @Test
    public void itExtractsHeaders() {
        request = new HttpRequest("GET / HTTP/1.1\r\n" +
                                  "Accept: text/plain\r\n" +
                                  "Content-Length: 20\r\n" +
                                  "\r\n");
        assertThat(request.headers.size(), is(equalTo(2)));
        assertThat(request.getContentLength(), is(equalTo("20")));
        assertThat(request.getAccept(), is(equalTo("text/plain")));
    }

    @Test
    public void itCanHandleALoneRequestLine() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        assertThat(request.headers.size(), is(equalTo(0)));
    }

    @Test
    public void itReadsTheBodyForPutRequests() {
        request = new HttpRequest("PUT /form HTTP/1.1\r\n\r\nsome data\r\n\r\n");
        assertThat(request.body, is(notNullValue()));
    }

    @Test
    public void itReadsTheBodyForPostRequests() {
        request = new HttpRequest("POST /form HTTP/1.1\r\n\r\nsome data\r\n\r\n");
        assertThat(request.body, is(notNullValue()));
    }
}
