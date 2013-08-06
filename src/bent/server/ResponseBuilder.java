package bent.server;

public class ResponseBuilder {
    public HttpResponse buildResponse(HttpRequest request) {
        HttpResponse response = new HttpResponse();

        String path = request.requestURI;

        if (path.equals("/")) {
            response.setStatusLine(request.httpVersion + " 200 OK");
        } else if (path.equals("/redirect")) {
            response.setStatusLine(request.httpVersion + " 302 Found");
            response.setLocation("http://localhost:5000/");
        } else {
            response.setStatusLine(request.httpVersion + " 404 Not Found");
        }

        return response;
    }
}
