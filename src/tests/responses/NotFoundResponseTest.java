package tests.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.responses.NotFoundResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class NotFoundResponseTest {
    @Test
    public void itReturns404NotFound() {
        HttpRequest request = new HttpRequest("GET /foobar HTTP/1.1");
        HttpResponse response = new NotFoundResponse();
        response.buildResponse(request);

        assertThat(response.getStatusLine(), containsString("HTTP/1.1 404 Not Found"));
    }
}
