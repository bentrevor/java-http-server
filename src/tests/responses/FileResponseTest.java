package tests.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.responses.FileResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockFileManager;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class FileResponseTest {
    private HttpRequest request;
    private HttpResponse response;
    private MockFileManager fakeFileSystem;

    @Before
    public void setUp() {
        fakeFileSystem = new MockFileManager();
    }

    @Test
    public void itReadsFile1() throws FileNotFoundException {
        fakeFileSystem.addFile("file1", "file1 contents");
        request = new HttpRequest("GET /file1 HTTP/1.1\r\n\r\n");
        response = new FileResponse(fakeFileSystem, request.getRequestURI());

        response.buildResponse(request);

        assertThat(response.toString(), containsString("file1 contents"));
    }

    @Test
    public void itReadsFile2() throws FileNotFoundException {
        fakeFileSystem.addFile("file2", "file2 contents");
        request = new HttpRequest("GET /file2 HTTP/1.1\r\n\r\n");
        response = new FileResponse(fakeFileSystem, request.getRequestURI());

        response.buildResponse(request);

        assertThat(response.toString(), containsString("file2 contents"));
    }

    @Test
    public void itOnlyAllowsGETRequests() throws FileNotFoundException {
        request = new HttpRequest("PUT /file2 HTTP/1.1\r\n\r\nsomething");
        response = new FileResponse(fakeFileSystem, request.getRequestURI());

        response.buildResponse(request);

        assertThat(response.toString(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void itSendsContentLengthWithImages() throws FileNotFoundException {
        fakeFileSystem.addFile("image.jpeg", "some content");
        request = new HttpRequest("GET /image.jpeg HTTP/1.1\r\n\r\n");
        response = new FileResponse(fakeFileSystem, request.getRequestURI());

        response.buildResponse(request);
        String fullResponse = response.toString();

        assertThat(fullResponse, containsString("Content-Length: 12"));
    }

    @Test
    public void itSendsContentTypeWithGifImages() throws FileNotFoundException {
        request = new HttpRequest("GET /image.gif HTTP/1.1\r\n\r\n");
        response = new FileResponse(fakeFileSystem, request.getRequestURI());

        response.buildResponse(request);
        String fullResponse = response.toString();

        assertThat(fullResponse, containsString("Content-Type: image/gif"));
    }

    @Test
    public void itSendsContentTypeWithPngImages() throws FileNotFoundException {
        request = new HttpRequest("GET /image.png HTTP/1.1\r\n\r\n");
        response = new FileResponse(fakeFileSystem, request.getRequestURI());

        response.buildResponse(request);
        String fullResponse = response.toString();

        assertThat(fullResponse, containsString("Content-Type: image/png"));
    }

    @Test
    public void itSendsContentTypeWithJpegImages() throws FileNotFoundException {
        request = new HttpRequest("GET /image.jpeg HTTP/1.1\r\n\r\n");
        response = new FileResponse(fakeFileSystem, request.getRequestURI());

        response.buildResponse(request);

        String fullResponse = response.toString();

        assertThat(fullResponse, containsString("Content-Type: image/jpeg"));
    }

    @Test
    public void itTrimsBodyAccordingToRangeHeader() throws FileNotFoundException {
        fakeFileSystem.addFile("partial_content.txt", "Contents of partial content file");
        request = new HttpRequest("GET /partial_content.txt HTTP/1.1\r\n" + 
                "Range: bytes=0-4\r\n" +
                "\r\n");
        response = new FileResponse(fakeFileSystem, request.getRequestURI());

        response.buildResponse(request);

        assertThat(response.toString(), containsString("HTTP/1.1 206 Partial Content"));
        assertThat(response.getStringBody().length(), is(4));
    }

    @Test
    public void itOpensANewFileAccordingToRequestURI() throws FileNotFoundException {
        request = new HttpRequest("GET /image.jpeg HTTP/1.1\r\n\r\n");
        response = new FileResponse(fakeFileSystem, request.getRequestURI());
        response = new FileResponse(fakeFileSystem, request.getRequestURI());
        response = new FileResponse(fakeFileSystem, request.getRequestURI());

        assertThat(fakeFileSystem.openFileCallCount, is(3));
        assertThat(fakeFileSystem.openFileArgument, is("/image.jpeg"));
    }

    @Test
    public void itReadsTheFileFromTheFileSystemToBuildAResponse() throws FileNotFoundException {
        request = new HttpRequest("GET /image.jpeg HTTP/1.1\r\n\r\n");
        response = new FileResponse(fakeFileSystem, request.getRequestURI());

        response.buildResponse(request);
        response.buildResponse(request);
        response.buildResponse(request);

        assertThat(fakeFileSystem.readFileCallCount, is(3));
    }

    @Test
    public void itHasAFilePath() {
        request = new HttpRequest("GET /image.jpeg HTTP/1.1\r\n\r\n");
        FileResponse response = new FileResponse(fakeFileSystem, request.getRequestURI());

        String filePath = response.getFilePath().toString();

        assertThat(filePath, is("mockroot/public/image.jpeg"));
    }
}
