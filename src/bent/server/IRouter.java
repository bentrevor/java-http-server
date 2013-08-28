package bent.server;

import java.util.Hashtable;

public interface IRouter {
    public Hashtable<String, HttpResponse> getRoutes();
}
