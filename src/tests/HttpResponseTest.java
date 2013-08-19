package tests;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.junit.matchers.JUnitMatchers.hasItems;

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

        assertThat(response.getStatusLine(), containsString("HTTP/1.1 200 OK"));
    }

    @Test
    public void itImplementsToString() {
        response.setContentLength(20);
        response.setStatusLine("HTTP/1.1 200 OK");
        response.setLocation("http://localhost:5000/");
        response.setContentType("image/jpeg");

        String fullResponse = response.toString();

        assertThat(fullResponse, containsString("HTTP/1.1 200 OK\r\n"));
        assertThat(fullResponse, containsString("Content-Length: 20\r\n"));
        assertThat(fullResponse, containsString("Content-Type: image/jpeg\r\n"));
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
        response.setBody("response body".getBytes());

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

    @Test
    public void itUsesANewStringBuilderForEachResponse() {
        HttpRequest fakeRequest = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        response.buildResponse(fakeRequest);

        String firstResponse = response.toString();
        String secondResponse = response.toString();

        assertThat(firstResponse.length(), is(equalTo(secondResponse.length())));
    }

    @Test
    public void itCanBeRepresentedAsByteArray() {
        response.setStatusLine("HTTP/1.1 200 OK");
        response.setBody("yo mama".getBytes());

        int responseLength = "HTTP/1.1 200 OK\r\n\r\nyo mama".length();

        assertThat(response.bytes().length, is(responseLength));
        assertThat(new String(response.bytes()), containsString("HTTP/1.1 200 OK"));
        assertThat(new String(response.bytes()), containsString("yo mama"));
    }

    @Test
    public void itDoesNotCastTheBodyToAString() {
        response.setStatusLine("HTTP/1.1 200 OK");

        byte[] fakeBody = new byte[10];

        for (int i = 0; i < 10; i++) {
            fakeBody[i] = (byte) (i - 5);
        }

        response.setBody(fakeBody);

        int offset = "HTTP/1.1 200 OK\r\n\r\n".length();

        assertThat(response.bytes()[0 + offset], is((byte) (-5)));
        assertThat(response.bytes()[1 + offset], is((byte) (-4)));
        assertThat(response.bytes()[2 + offset], is((byte) (-3)));
        assertThat(response.bytes()[3 + offset], is((byte) (-2)));
        assertThat(response.bytes()[4 + offset], is((byte) (-1)));
    }
}
