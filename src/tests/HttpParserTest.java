package tests;

import bent.server.HttpParser;
import bent.server.HttpRequest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Hashtable;

import static junit.framework.Assert.*;

@RunWith(JUnit4.class)
public class HttpParserTest {
    HttpParser parser = null;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        parser = new HttpParser();
    }

    @Test
    public void itConvertsStringToHttpRequest() {
        parser.parse("GET / HTTP/1.1\r\n\r\n");
        assertTrue(parser.parsedRequest instanceof HttpRequest);
    }

    @Test
    public void itExtractsTheMethodFromRequestLine() {
        parser.parse("GET / HTTP/1.1\r\n\r\n");
        assertEquals("GET", parser.parsedRequest.method);

        parser.parse("PUT / HTTP/1.1\r\n\r\n");
        assertEquals("PUT", parser.parsedRequest.method);
    }

    @Test
    public void itExtractsThePathFromRequestLine() {
        parser.parse("GET / HTTP/1.1\r\n\r\n");
        assertEquals("/", parser.parsedRequest.requestURI);

        parser.parse("PUT /peanuts HTTP/1.1\r\n\r\n");
        assertEquals("/peanuts", parser.parsedRequest.requestURI);
    }

    @Test
    public void itExtractsTheHttpVersionFromRequestLine() {
        parser.parse("GET / HTTP/1.1\r\n\r\n");
        assertEquals("HTTP/1.1", parser.parsedRequest.httpVersion);
    }

    @Test
    public void itRaisesAnErrorForInvalidRequests() {
        exception.expect(ArrayIndexOutOfBoundsException.class);
        parser.parse("invalid");
    }
}
