package bent.server;

import bent.server.responses.FormResponse;
import bent.server.responses.RedirectResponse;
import bent.server.responses.RootResponse;

import java.util.Hashtable;

public class CobSpecRouter implements IRouter {
    private Hashtable<String, HttpResponse> routes;

    public Hashtable<String, HttpResponse> getRoutes() {
        routes = new Hashtable<>();
        initializeRoutes();
        return routes;
    }

    private void initializeRoutes() {
        routes.put("/", new RootResponse());
        routes.put("/redirect", new RedirectResponse());
        routes.put("/form", new FormResponse());
    }
}
