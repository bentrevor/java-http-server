package bent.server.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;

public class FormResponse extends HttpResponse {
    public void buildResponse(HttpRequest request) {
        setStatusLine("HTTP/1.1 200 OK");

        if (putOrPost(request)) {
            String[] data = request.body.split("=");
            setBody(data[0] + " = " + data[1]);
        }
    }

    private boolean putOrPost(HttpRequest request) {
        return request.getMethod().equals("POST") || request.getMethod().equals("PUT");
    }
}

