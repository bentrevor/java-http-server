package bent.server;

import java.util.Hashtable;

public class ResponseBuilder implements IResponseBuilder {
    public HttpResponse buildResponse(HttpRequest request) {
        HttpResponse response;
        CobSpecRouter router = new CobSpecRouter();
        Hashtable<String, HttpResponse> routes = router.getRoutes();

        String path = request.getRequestURI();

        response = routes.get(path);

        if (response == null) {
            response = new HttpResponse();
        }

        response.buildResponse(request);
        return response;
    }
}
