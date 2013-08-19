package bent.server.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;

public class RootResponse extends HttpResponse {
    public RootResponse() {
    }

    public void buildResponse(HttpRequest request) {
        if (request.getMethod().equals("GET")) {
            setStatusLine("HTTP/1.1 200 OK");
            setBody(homePage());
        }
    }

    private String homePage() {
        StringBuilder page = new StringBuilder();

        page.append("<!DOCTYPE html><html><head></head><body>");
        page.append("<a href=\"/file1\">file1</a>");
        page.append("<a href=\"/file2\">file2</a>");
        page.append("<a href=\"/image.gif\">image.gif</a>");
        page.append("<a href=\"/image.jpeg\">image.jpeg</a>");
        page.append("<a href=\"/image.png\">image.png</a>");
        page.append("<a href=\"/text-file.txt\">text-file.txt</a>");
        page.append("<a href=\"/partial_content.txt\">partial_content.txt</a>");
        page.append("</body></html>");

        return page.toString();
    }
}
