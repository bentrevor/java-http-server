package bent.server.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;

public class FormResponse extends HttpResponse {
    public void buildResponse(HttpRequest request) {
        setStatusLine("HTTP/1.1 200 OK");
    }
}

