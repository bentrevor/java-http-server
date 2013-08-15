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
    HttpRequest request;
    HttpResponse response;

    @Test
    public void itReadsFile1() throws FileNotFoundException {
        request = new HttpRequest("GET /file1 HTTP/1.1\r\n\r\n");
        response = new FileResponse(request.getRequestURI());

        response.buildResponse(request);

        assertThat(response.toString(), containsString("file1 contents"));
    }

    @Test
    public void itReadsFile2() throws FileNotFoundException {
        request = new HttpRequest("GET /file2 HTTP/1.1\r\n\r\n");
        response = new FileResponse(request.getRequestURI());

        response.buildResponse(request);

        assertThat(response.toString(), containsString("file2 contents"));
    }

    @Test
    public void itOnlyAllowsGETRequests() throws FileNotFoundException {
        request = new HttpRequest("PUT /file2 HTTP/1.1\r\n\r\nsomething");
        response = new FileResponse(request.getRequestURI());

        response.buildResponse(request);

        assertThat(response.toString(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void itSendsContentLengthWithImages() throws FileNotFoundException {
        request = new HttpRequest("GET /image.jpeg HTTP/1.1\r\n\r\n");
        response = new FileResponse(request.getRequestURI());

        response.buildResponse(request);
        String fullResponse = response.toString();

        assertThat(fullResponse, containsString("Content-Length: 38400"));
    }

    @Test
    public void itSendsContentTypeWithGifImages() throws FileNotFoundException {
        request = new HttpRequest("GET /image.gif HTTP/1.1\r\n\r\n");
        response = new FileResponse(request.getRequestURI());

        response.buildResponse(request);
        String fullResponse = response.toString();

        assertThat(fullResponse, containsString("Content-Type: image/gif"));
    }

    @Test
    public void itSendsContentTypeWithPngImages() throws FileNotFoundException {
        request = new HttpRequest("GET /image.png HTTP/1.1\r\n\r\n");
        response = new FileResponse(request.getRequestURI());

        response.buildResponse(request);
        String fullResponse = response.toString();

        assertThat(fullResponse, containsString("Content-Type: image/png"));
    }

    @Test
    public void itSendsContentTypeWithJpegImages() throws FileNotFoundException {
        request = new HttpRequest("GET /image.jpeg HTTP/1.1\r\n\r\n");
        response = new FileResponse(request.getRequestURI());

        response.buildResponse(request);

        String fullResponse = response.toString();

        assertThat(fullResponse, containsString("Content-Type: image/jpeg"));
    }
}
