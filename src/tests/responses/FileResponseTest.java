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
    public void itReadsFile1() throws FileNotFoundException {
        HttpRequest request = new HttpRequest("GET /file1 HTTP/1.1\r\n\r\n");
        HttpResponse response = new FileResponse("/file1");

        response.buildResponse(request);

        assertThat(response.toString(), containsString("file1 contents"));
    }

    @Test
    public void itReadsFile2() throws FileNotFoundException {
        HttpRequest request = new HttpRequest("GET /file2 HTTP/1.1\r\n\r\n");
        HttpResponse response = new FileResponse("/file2");

        response.buildResponse(request);

        assertThat(response.toString(), containsString("file2 contents"));
    }

    @Test
    public void itOnlyAllowsGETRequests() throws FileNotFoundException {
        HttpRequest request = new HttpRequest("PUT /file2 HTTP/1.1\r\n\r\nsomething");
        HttpResponse response = new FileResponse("/file2");

        response.buildResponse(request);

        assertThat(response.toString(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }
}
