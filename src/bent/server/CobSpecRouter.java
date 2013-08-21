package bent.server;

import bent.server.responses.*;

import java.io.FileNotFoundException;
import java.util.Hashtable;

public class CobSpecRouter implements IRouter {
    private Hashtable<String, HttpResponse> routes;
    private IFileSystem fileSystem;

    public CobSpecRouter(IFileSystem fs) {
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
        try {
            routes.put("/file1",               new FileResponse(fileSystem, "/file1"));
            routes.put("/file2",               new FileResponse(fileSystem, "/file2"));
            routes.put("/image.jpeg",          new FileResponse(fileSystem, "/image.jpeg"));
            routes.put("/image.png",           new FileResponse(fileSystem, "/image.png"));
            routes.put("/image.gif",           new FileResponse(fileSystem, "/image.gif"));
            routes.put("/text-file.txt",       new FileResponse(fileSystem, "/text-file.txt"));
            routes.put("/partial_content.txt", new FileResponse(fileSystem, "/partial_content.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public IFileSystem getFileSystem() {
        return fileSystem;
    }
}
