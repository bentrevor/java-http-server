package bent.server.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;

public class RedirectResponse extends HttpResponse {
    public void buildResponse(HttpRequest request) {
        setStatusLine("HTTP/1.1 301 Moved Permanently");
        setLocation("http://localhost:5000/");
    }
}
