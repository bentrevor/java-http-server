package bent.server;

public class ResponseBuilder {
    public HttpResponse buildResponse(HttpRequest request) {
        HttpResponse response = new HttpResponse();

        String path = request.getRequestURI();

        if (path.equals("/")) {
            response.setStatusLine(request.getHttpVersion() + " 200 OK");
        } else if (path.equals("/redirect")) {
            response.setStatusLine(request.getHttpVersion() + " 302 Found");
            response.setLocation("http://localhost:5000/");
        } else {
            response.setStatusLine(request.getHttpVersion() + " 404 Not Found");
        }

        return response;
    }
}
