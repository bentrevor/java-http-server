package bent.server.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;

public class RootResponse extends HttpResponse {
    public RootResponse() {
    }

    public void buildResponse(HttpRequest request) {
        if (request.getMethod().equals("GET")) {
            setStatusLine("HTTP/1.1 200 OK");
        }
    }
}
