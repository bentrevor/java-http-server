package bent.server;

import bent.server.sockets.ISocket;

public class HandlerFactory implements IHandlerFactory {
    private IRouter applicationRouter;

    public HandlerFactory(IRouter router) {
        applicationRouter = router;
    }

    public RequestHandler makeHandler(ISocket socket) {
        return new RequestHandler(socket,
                                  new RequestReader(),
                                  new ResponseBuilder(applicationRouter),
                                  new ResponseWriter());
    }
}
