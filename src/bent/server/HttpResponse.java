package bent.server;

import java.util.LinkedList;

public class HttpResponse {
    public LinkedList<String> headers;
    public StringBuilder response;
    public String statusLine;

    public HttpResponse() {
        response = new StringBuilder();
        headers = new LinkedList<>();
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

    public String toString() {
        response.append(statusLine + "\r\n");

        for (String header : headers) {
            response.append(header + "\r\n");
        }

        response.append("\r\n");

        return response.toString();
    }
}
