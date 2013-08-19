package bent.server.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;

public class NotFoundResponse extends HttpResponse {
    public void buildResponse(HttpRequest request) {
        setStatusLine("HTTP/1.1 404 Not Found");
    }
}
