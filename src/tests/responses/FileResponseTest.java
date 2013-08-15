package tests.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.responses.FileResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class FileResponseTest {
    @Test
    public void itHasARouteForFile1() throws FileNotFoundException {
        HttpRequest request = new HttpRequest("GET /file1 HTTP/1.1\r\n\r\n");
        HttpResponse response = new FileResponse("/file1");

        response.buildResponse(request);

        assertThat(response.toString(), containsString("file1 contents"));
    }
}
