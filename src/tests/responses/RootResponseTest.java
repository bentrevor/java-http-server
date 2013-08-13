package tests.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.responses.RootResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class RootResponseTest {
    @Test
    public void itReturns200Status() {
        HttpRequest request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        HttpResponse response = new RootResponse();
        response.buildResponse(request);

        assertThat(response.statusLine, is("HTTP/1.1 200 OK"));
    }
}
