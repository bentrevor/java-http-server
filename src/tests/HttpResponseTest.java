package tests;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class HttpResponseTest {
    private HttpResponse response;

    @Before
    public void setUp() {
        response = new HttpResponse();
    }

    @Test
    public void itHasAStatusLine() {
        response.setStatusLine("HTTP/1.1 200 OK");

        assertThat(response.statusLine, containsString("HTTP/1.1 200 OK"));
    }

    @Test
    public void itImplementsToString() {
        response.setContentLength(20);
        response.setStatusLine("HTTP/1.1 200 OK");
        response.setLocation("http://localhost:5000/");

        String fullResponse = response.toString();

        assertThat(fullResponse, containsString("HTTP/1.1 200 OK\r\n"));
        assertThat(fullResponse, containsString("Content-Length: 20\r\n"));
        assertThat(fullResponse, containsString("Location: http://localhost:5000/\r\n"));
    }

    @Test
    public void itOnlyAppendsHeadersThatHaveBeenSet() {
        response.setStatusLine("HTTP/1.1 200 OK");

        String fullResponse = response.toString();

        assertThat(fullResponse, containsString("HTTP/1.1 200 OK\r\n"));
        assertThat(fullResponse, not(containsString("Content-Length:")));
        assertThat(fullResponse, not(containsString("Location:")));
    }

    @Test
    public void itCanHaveABodyAfterTheHeaders() {
        response.setStatusLine("HTTP/1.1 200 OK");
        response.setBody("response body");

        String fullResponse = response.toString();

        assertThat(fullResponse, containsString("\r\n\r\nresponse body"));
    }

    @Test
    public void itBuildsADefault404Response() {
        HttpRequest fakeRequest = new HttpRequest("GET /foobar HTTP/1.1\r\n\r\n");
        response.buildResponse(fakeRequest);

        String fullResponse = response.toString();

        assertThat(fullResponse, containsString("HTTP/1.1 404 Not Found"));
    }
}
