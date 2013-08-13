package bent.server;

import bent.server.responses.RootResponse;

import java.util.Hashtable;

public class CobSpecRouter {
    private Hashtable<String, HttpResponse> routes;

    public Hashtable<String, HttpResponse> getRoutes() {
        routes = new Hashtable<>();
        routes.put("/", new RootResponse());
        return routes;
    }
}
