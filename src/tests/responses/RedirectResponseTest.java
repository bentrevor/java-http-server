package tests.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.responses.RedirectResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class RedirectResponseTest {
    @Test
    public void itRedirectsToRootPath() {
        HttpRequest request = new HttpRequest("GET /redirect HTTP/1.1\r\n\r\n");
        HttpResponse response = new RedirectResponse();
        response.buildResponse(request);

        assertThat(response.getStatusLine(), is("HTTP/1.1 301 Moved Permanently"));
        assertThat(response.toString(), containsString("Location: http://localhost:5000/"));
    }
}
