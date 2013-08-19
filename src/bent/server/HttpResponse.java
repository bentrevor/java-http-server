package bent.server;

import java.util.LinkedList;

public abstract class HttpResponse {
    private LinkedList<String> headers;
    private StringBuilder responseBuilder;
    private String statusLine;
    private byte[] body;

    public abstract void buildResponse(HttpRequest request);

    public HttpResponse() {
        headers = new LinkedList<>();
        body = new byte[0];
    }

    public String toString() {
        return getStringHeaders() + getStringBody();
    }

    private String getStringHeaders() {
        responseBuilder = new StringBuilder();
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

    protected void setBody(String content) {
        body = content.getBytes();
    }

    public void setBody(byte[] bytes) {
        body = bytes;
    }

    public String getStringBody() {
        try {
            return new String(body);
        } catch (NullPointerException e) {
            return "";
        }
    }

    public void setContentType(String type) {
        headers.add("Content-Type: " + type);
    }

    public String getStatusLine() {
        return statusLine;
    }

    public byte[] bytes() {
        int headersLength = getStringHeaders().length();
        int responseLength = headersLength + body.length;
        byte[] response = new byte[responseLength];

        for (int i = 0; i < headersLength; i++) {
            response[i] = (byte) getStringHeaders().charAt(i);
        }

        for (int j = headersLength; j < responseLength; j++) {
            response[j] = body[j - headersLength];
        }

        return response;
    }
}
