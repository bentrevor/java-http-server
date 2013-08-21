package bent.server;

import bent.server.responses.NotFoundResponse;

import java.util.Hashtable;

public class ResponseBuilder implements IResponseBuilder {
    public Hashtable<String, HttpResponse> routes;
    public HttpResponse response;
    private IRouter router;

    public ResponseBuilder(IRouter someRouter) {
        router = someRouter;
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

    public IRouter getRouter() {
        return router;
    }
}
