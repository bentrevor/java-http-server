package bent.server;

import bent.server.responses.*;

import java.io.FileNotFoundException;
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
        routes.put("/parameters", new ParametersResponse());
        try {
            routes.put("/file1", new FileResponse("/file1"));
            routes.put("/file2", new FileResponse("/file2"));
            routes.put("/image.jpeg", new FileResponse("/image.jpeg"));
            routes.put("/image.png", new FileResponse("/image.png"));
            routes.put("/image.gif", new FileResponse("/image.gif"));
            routes.put("/text-file.txt", new FileResponse("/text-file.txt"));
            routes.put("/partial_content.txt", new FileResponse("/partial_content.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
