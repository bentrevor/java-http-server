package tests;

import static junit.framework.Assert.*;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.ResponseBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ResponseBuilderTest {
    ResponseBuilder builder = null;
    HttpRequest request = null;
    HttpResponse response = null;

    @Before
    public void setUp() {
        builder = new ResponseBuilder();
    }

    @Test
    public void itBuildsASuccessResponse() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");

        response = builder.buildResponse(request);

        assertTrue(response.toString().contains("HTTP/1.1 200 OK\r\n"));
    }

    @Test
    public void itBuildsAFailureResponse() {
        request = new HttpRequest("GET /foobar HTTP/1.1\r\n\r\n");

        response = builder.buildResponse(request);

        assertTrue(response.toString().contains("HTTP/1.1 404 Not Found\r\n"));
    }

    @Test
    public void itHandlesARedirectRoute() {
        request = new HttpRequest("GET /redirect HTTP/1.1\r\n\r\n");

        response = builder.buildResponse(request);

        assertTrue(response.toString().contains("HTTP/1.1 302 Found\r\n"));
        assertTrue(response.toString().contains("Location: http://localhost:5000/\r\n"));
    }
}
