package bent.server;

import java.util.Hashtable;

public class HttpParser {
    public HttpRequest parsedRequest = null;

    public HttpRequest parse(String request) {
        parsedRequest = new HttpRequest();
        String headers = request.split("\r\n\r\n")[0];

        extractMethodFrom(headers);
        extractRequestURIFrom(headers);
        extractHttpVersionFrom(headers);

        return parsedRequest;
    }

    public void extractMethodFrom(String headers) {
        String method = headers.split(" ")[0];
        parsedRequest.put("Method", method);
    }

    public void extractRequestURIFrom(String headers) {
        String requestURI = headers.split(" ")[1];
        parsedRequest.put("Request-URI", requestURI);
    }

    public void extractHttpVersionFrom(String headers) {
        String httpVersion = headers.split(" ")[2];
        parsedRequest.put("HTTP-Version", httpVersion);
    }
}
