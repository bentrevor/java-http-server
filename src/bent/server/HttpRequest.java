package bent.server;

import java.util.Hashtable;

public class HttpRequest {
    public Hashtable<String, String> headers;
    public String body;
    public String fullHeaders;
    private String method;
    private String requestURI;
    private String httpVersion;

    public HttpRequest(String request) {
        body = null;
        fullHeaders = request.split("\r\n\r\n")[0];
        headers = new Hashtable<>();

        setRequestLine();

        if (headersProvided()) {
            setHeaders();
        }

        if (putOrPostRequest() && bodySentWith(request)) {
            body = request.split("\r\n\r\n")[1];
        }
    }

    private void setRequestLine() {
        String requestLine = fullHeaders.split("\r\n", 2)[0];
        method = requestLine.split(" ")[0];
        requestURI = requestLine.split(" ")[1];
        httpVersion = requestLine.split(" ")[2];
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

    private boolean putOrPostRequest() {
        return method.equals("PUT") || method.equals("POST");
    }

    private boolean bodySentWith(String request) {
        return request.split("\r\n\r\n").length > 1;
    }

    public String getMethod() {
        return method;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getContentLength() {
        return headers.get("content-length");
    }

    public String getAccept() {
        return headers.get("accept");
    }
}
