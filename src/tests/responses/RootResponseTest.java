package tests.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.responses.RootResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class RootResponseTest {
    @Test
    public void itReturns200Status() {
        HttpRequest request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        HttpResponse response = new RootResponse();
        response.buildResponse(request);

        assertThat(response.getStatusLine(), is("HTTP/1.1 200 OK"));
    }

    @Test
    public void itShowsTheDirectoryLinksInHomeHtml() {
        HttpRequest request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");
        HttpResponse response = new RootResponse();
        response.buildResponse(request);

        assertThat(response.getStringBody(), containsString("<a href=\"/file1\">file1</a>"));
        assertThat(response.getStringBody(), containsString("<a href=\"/file2\">file2</a>"));
        assertThat(response.getStringBody(), containsString("<a href=\"/image.gif\">image.gif</a>"));
        assertThat(response.getStringBody(), containsString("<a href=\"/image.jpeg\">image.jpeg</a>"));
        assertThat(response.getStringBody(), containsString("<a href=\"/image.png\">image.png</a>"));
        assertThat(response.getStringBody(), containsString("<a href=\"/text-file.txt\">text-file.txt</a>"));
        assertThat(response.getStringBody(), containsString("<a href=\"/partial_content.txt\">partial_content.txt</a>"));
    }
}
