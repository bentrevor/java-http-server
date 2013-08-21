package bent.server;

public class HandlerFactory implements IHandlerFactory {
    private String publicDirectoryPath;

    public HandlerFactory(String path) {
        publicDirectoryPath = path;
    }

    public RequestHandler makeHandler() {
        return new RequestHandler(new RequestReader(),
               new ResponseBuilder(new CobSpecRouter(new FileSystem(publicDirectoryPath))),
               new ResponseWriter());
    }

    public String getPublicDir() {
        return publicDirectoryPath;
    }
}
