package bent.server;

import bent.server.responses.NotFoundResponse;

import java.util.Hashtable;

public class ResponseBuilder implements IResponseBuilder {
    public Hashtable<String, HttpResponse> routes;
    public HttpResponse response;

    public ResponseBuilder(IRouter router) {
        response = null;
        routes = router.getRoutes();
    }

    public HttpResponse buildResponse(HttpRequest request) {
        String path = request.getRequestURI();

        response = routes.get(path);

        if (response == null) {
            response = new NotFoundResponse();
        }

        response.buildResponse(request);
        return response;
    }
}
