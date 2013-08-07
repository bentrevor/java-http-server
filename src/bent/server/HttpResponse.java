package bent.server;

import java.util.LinkedList;

public class HttpResponse {
    public LinkedList<String> headers;
    public StringBuilder responseBuilder;
    public String statusLine;

    public HttpResponse() {
        responseBuilder = new StringBuilder();
        headers = new LinkedList<>();
    }

    public String toString() {
        responseBuilder.append(statusLine + "\r\n");

        for (String header : headers) {
            responseBuilder.append(header + "\r\n");
        }

        responseBuilder.append("\r\n");

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
}
