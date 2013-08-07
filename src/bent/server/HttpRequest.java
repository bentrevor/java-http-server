package bent.server;

import java.util.Hashtable;

public class HttpRequest {
    public Hashtable<String, String> headers;
    public String requestLine;
    public String body;
    public String fullHeaders;

    public HttpRequest(String request) {
        body = null;
        fullHeaders = request.split("\r\n\r\n")[0];
        headers = new Hashtable<>();

        setRequestLine();

        if (headersProvided()) {
            setHeaders();
        }
    }

    private void setRequestLine() {
        requestLine = fullHeaders.split("\r\n", 2)[0];
    }

    private boolean headersProvided() {
        return fullHeaders.split("\r\n").length > 1;
    }

    private void setHeaders() {
        String[] arrayOfHeaders = fullHeaders.split("\r\n", 2)[1].split("\r\n");

        for (String header : arrayOfHeaders) {
            String headerName = header.split(":")[0].toLowerCase();
            String value = header.split(":")[1].trim();

            headers.put(headerName, value);
        }
    }

    public String getMethod() {
        return requestLine.split(" ")[0];
    }

    public String getRequestURI() {
        return requestLine.split(" ")[1];
    }

    public String getHttpVersion() {
        return requestLine.split(" ")[2];
    }

    public String getContentLength() {
        return headers.get("content-length");
    }

    public String getAccept() {
        return headers.get("accept");
    }
}
