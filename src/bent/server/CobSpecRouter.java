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
        FileSystem fs = new FileSystem();
        routes.put("/", new RootResponse());
        routes.put("/redirect", new RedirectResponse());
        routes.put("/form", new FormResponse());
        routes.put("/parameters", new ParametersResponse());
        try {
            routes.put("/file1", new FileResponse(fs, "/file1"));
            routes.put("/file2", new FileResponse(fs, "/file2"));
            routes.put("/image.jpeg", new FileResponse(fs, "/image.jpeg"));
            routes.put("/image.png", new FileResponse(fs, "/image.png"));
            routes.put("/image.gif", new FileResponse(fs, "/image.gif"));
            routes.put("/text-file.txt", new FileResponse(fs, "/text-file.txt"));
            routes.put("/partial_content.txt", new FileResponse(fs, "/partial_content.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
