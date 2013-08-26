package bent.server;

public class ThreadedHandlerFactory implements IHandlerFactory {
    private IHandlerFactory handlerFactory;

    public ThreadedHandlerFactory(IHandlerFactory factory) {
        handlerFactory = factory;
    }

    public IRequestHandler makeHandler() {
        return handlerFactory.makeHandler();
    }
}
