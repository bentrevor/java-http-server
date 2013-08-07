package tests;

import bent.server.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class HttpResponseTest {

    @Test
    public void itHasAStatusLine() {
        HttpResponse response = new HttpResponse();
        response.setStatusLine("HTTP/1.1 200 OK");

        assertThat(response.statusLine, containsString("HTTP/1.1 200 OK"));
    }

    @Test
    public void itImplementsToString() {
        HttpResponse response = new HttpResponse();
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
        HttpResponse response = new HttpResponse();
        response.setStatusLine("HTTP/1.1 200 OK");

        String fullResponse = response.toString();

        assertThat(fullResponse, containsString("HTTP/1.1 200 OK\r\n"));
        assertThat(fullResponse, not(containsString("Content-Length:")));
        assertThat(fullResponse, not(containsString("Location:")));
    }
}
