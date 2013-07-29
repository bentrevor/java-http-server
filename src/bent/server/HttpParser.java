package bent.server;

import java.util.Hashtable;

public class HttpParser {
    public Hashtable<String, String> parsedRequest = null;

    public HttpParser() {
        parsedRequest = new Hashtable<String, String>();
    }

    public Hashtable<String, String> parse(String request) {
        extractMethodFrom(request);
        extractRequestURIFrom(request);
        extractHttpVersionFrom(request);

        return parsedRequest;
    }

    public void extractMethodFrom(String request) {
        String method = request.split(" ")[0];
        parsedRequest.put("Method", method);
    }

    public void extractRequestURIFrom(String request) {
        String requestURI = request.split(" ")[1];
        parsedRequest.put("Request-URI", requestURI);
    }

    public void extractHttpVersionFrom(String request) {
        String httpVersion = request.split(" ")[2];
        parsedRequest.put("HTTP-Version", httpVersion);
    }
}
