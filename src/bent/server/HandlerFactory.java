package bent.server;

import bent.server.sockets.ISocket;

import java.io.IOException;

public class HandlerFactory implements IHandlerFactory {
    private IRouter applicationRouter;

    public HandlerFactory(IRouter router) {
        applicationRouter = router;
    }

    public RequestHandler makeHandler(ISocket socket) throws IOException {
        return new RequestHandler(socket,
                                  new RequestReader(),
                                  new ResponseBuilder(applicationRouter),
                                  new ResponseWriter());
    }
}
