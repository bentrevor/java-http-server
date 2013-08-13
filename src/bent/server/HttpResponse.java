package bent.server;

import java.util.LinkedList;

public class HttpResponse {
    private LinkedList<String> headers;
    private StringBuilder responseBuilder;
    public String statusLine;
    private String body;

    public void buildResponse(HttpRequest request) {
    }

    public HttpResponse() {
        responseBuilder = new StringBuilder();
        headers = new LinkedList<>();
        body = "";
    }

    public String toString() {
        responseBuilder.append(statusLine + "\r\n");

        for (String header : headers) {
            responseBuilder.append(header + "\r\n");
        }

        responseBuilder.append("\r\n");
        responseBuilder.append(body);

        return responseBuilder.toString();
    }

    public void setStatusLine(String status) {
        statusLine = status;
    }

    public void setContentLength(int length) {
        headers.add("Content-Length: " + length);
    }

    public void setLocation(String location) {
        headers.add("Location: " + location);
    }

    public void setBody(String content) {
        body = content;
    }
}
