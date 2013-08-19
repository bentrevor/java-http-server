package tests;

import bent.server.HttpRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class HttpRequestTest {
    private HttpRequest request;

    @Test
    public void itExtractsTheMethodFromRequestLine() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        assertThat(request.getMethod(), is(equalTo("GET")));

        request = new HttpRequest("PUT / HTTP/1.1\r\n" +
                                  "\r\n" +
                                  "body\r\n\r\n");
        assertThat(request.getMethod(), is(equalTo("PUT")));
    }

    @Test
    public void itExtractsThePathFromRequestLine() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        assertThat(request.getRequestURI(), is(equalTo("/")));

        request = new HttpRequest("PUT /peanuts HTTP/1.1\r\n" +
                                  "\r\n" +
                                  "body\r\n\r\n");
        assertThat(request.getRequestURI(), is(equalTo("/peanuts")));
    }

    @Test
    public void itExtractsTheHttpVersionFromRequestLine() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        assertThat(request.getHttpVersion(), is(equalTo("HTTP/1.1")));
    }

    @Test
    public void itExcludesRequestLineFromHeaders() {
        request = new HttpRequest("GET / HTTP/1.1\r\n" +
                                  "Accept: text/plain\r\n" +
                                  "Content-Length: 0\r\n\r\n");
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
        request = new HttpRequest("PUT /form HTTP/1.1\r\n" +
                                  "\r\n" +
                                  "some data\r\n\r\n");
        assertThat(request.body, containsString("some data"));
    }

    @Test
    public void itReadsTheBodyForPostRequests() {
        request = new HttpRequest("POST /form HTTP/1.1\r\n" +
                                  "\r\n" +
                                  "some data\r\n\r\n");
        assertThat(request.body, containsString("some data"));
    }

    @Test
    public void itChecksThatABodyWasProvided() {
        request = new HttpRequest("PUT /form HTTP/1.1\r\n\r\n");
        assertThat(request.body, is(""));
    }

    @Test
    public void itDisregardsQueryStringParametersInRequestURI() {
        request = new HttpRequest("GET /parameters?blah HTTP/1.1\r\n\r\n");
        assertThat(request.getRequestURI(), is(equalTo("/parameters")));
    }

    @Test
    public void itExtractsQueryStringParametersIntoMemberVariable() {
        request = new HttpRequest("GET /parameters?blah HTTP/1.1\r\n\r\n");
        assertThat(request.queryStringParams, containsString("blah"));
    }

    @Test
    public void itCanSetARangeHeader() {
        request = new HttpRequest("GET / HTTP/1.1\r\n" +
                                  "Range: bytes=0-4\r\n" +
                                  "\r\n");
        assertThat(request.getRange(), containsString("bytes=0-4"));
    }
}
