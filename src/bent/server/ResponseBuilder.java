package bent.server;

import java.util.Hashtable;

public class ResponseBuilder implements IResponseBuilder {
    private Hashtable<String, HttpResponse> routes;
    public ResponseBuilder(IRouter router) {
        routes = router.getRoutes();
    }

    public HttpResponse buildResponse(HttpRequest request) {
        HttpResponse response;

        String path = request.getRequestURI();

        response = routes.get(path);

        if (response == null) {
            response = new HttpResponse();
        }

        response.buildResponse(request);
        return response;
    }
}
