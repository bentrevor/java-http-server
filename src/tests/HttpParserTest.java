package tests;

import bent.server.HttpParser;
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
    public void itTakesAStringAndReturnsAHash() {
        parser.parse("GET / HTTP/1.1\r\n\r\n");
        assertTrue(parser.parsedRequest instanceof Hashtable);
    }

    @Test
    public void itExtractsTheMethodFromRequestLine() {
        parser.parse("GET / HTTP/1.1\r\n\r\n");
        assertEquals("GET", parser.parsedRequest.get("Method"));

        parser.parse("PUT / HTTP/1.1\r\n\r\n");
        assertEquals("PUT", parser.parsedRequest.get("Method"));
    }

    @Test
    public void itExtractsThePathFromRequestLine() {
        parser.parse("GET / HTTP/1.1\r\n\r\n");
        assertEquals("/", parser.parsedRequest.get("Request-URI"));

        parser.parse("PUT /peanuts HTTP/1.1\r\n\r\n");
        assertEquals("/peanuts", parser.parsedRequest.get("Request-URI"));
    }

    @Test
    public void itExtractsTheHttpVersionFromRequestLine() {
        parser.parse("GET / HTTP/1.1\r\n\r\n");
        assertEquals("HTTP/1.1", parser.parsedRequest.get("HTTP-Version"));
    }

    @Test
    public void itRaisesAnErrorForInvalidRequests() {
        exception.expect(ArrayIndexOutOfBoundsException.class);
        parser.parse("invalid");
    }
}
