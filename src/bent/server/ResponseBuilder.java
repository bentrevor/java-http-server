package bent.server;

import bent.server.responses.RootResponse;

public class ResponseBuilder implements IResponseBuilder {
    public HttpResponse buildResponse(HttpRequest request) {
        HttpResponse response = new HttpResponse();

        String path = request.getRequestURI();

        if (path.equals("/")) {
            response = new RootResponse(request);
        } else if (path.equals("/redirect")) {
            response.setStatusLine(request.getHttpVersion() + " 302 Found");
            response.setLocation("http://localhost:5000/");
        } else if (path.equals("/form")) {
            response.setStatusLine(request.getHttpVersion() + " 200 OK");
        } else {
            response.setStatusLine(request.getHttpVersion() + " 404 Not Found");
        }

        return response;
    }
}
