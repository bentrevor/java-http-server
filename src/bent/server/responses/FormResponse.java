package bent.server.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;

public class FormResponse extends HttpResponse {
    public void buildResponse(HttpRequest request) {
        setStatusLine("HTTP/1.1 200 OK");

        if (request.getMethod().equals("POST")) {
            String[] data = request.body.split("=");
            setBody(data[0] + " = " + data[1]);
        }
    }
}

