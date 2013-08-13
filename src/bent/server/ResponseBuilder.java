package bent.server;

import bent.server.responses.FormResponse;
import bent.server.responses.RedirectResponse;
import bent.server.responses.RootResponse;

public class ResponseBuilder implements IResponseBuilder {
    public HttpResponse buildResponse(HttpRequest request) {
        HttpResponse response = new HttpResponse();

        String path = request.getRequestURI();

        if (path.equals("/")) {
            response = new RootResponse();
            response.buildResponse(request);
        } else if (path.equals("/redirect")) {
            response = new RedirectResponse();
            response.buildResponse(request);
        } else if (path.equals("/form")) {
            response = new FormResponse();
            response.buildResponse(request);
        } else {
            response.setStatusLine(request.getHttpVersion() + " 404 Not Found");
        }

        return response;
    }
}
