package bent.server;

public class HandlerFactory implements IHandlerFactory {
    private IRouter applicationRouter;

    public HandlerFactory(IRouter router) {
        applicationRouter = router;
    }

    public RequestHandler makeHandler() {
        return new RequestHandler(new RequestReader(),
               new ResponseBuilder(applicationRouter),
               new ResponseWriter());
    }
}
