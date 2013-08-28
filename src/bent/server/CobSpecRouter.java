package bent.server;

import bent.server.responses.*;

import java.util.Hashtable;

public class CobSpecRouter implements IRouter {
    private Hashtable<String, HttpResponse> routes;
    private IFileManager fileSystem;

    public CobSpecRouter(IFileManager fs) {
        fileSystem = fs;
        routes = new Hashtable<>();
        initializeRoutes();
    }

    public Hashtable<String, HttpResponse> getRoutes() {
        return routes;
    }

    private void initializeRoutes() {
        routes.put("/",           new RootResponse());
        routes.put("/redirect",   new RedirectResponse());
        routes.put("/form",       new FormResponse());
        routes.put("/parameters", new ParametersResponse());
        addFileRoutes();
    }

    private void addFileRoutes() {
        String[] filenames = fileSystem.getPublicDirectory().list();
        for (String filename : filenames) {
            filename = "/" + filename;
            routes.put(filename, new FileResponse(fileSystem, filename));
        }
    }
}
