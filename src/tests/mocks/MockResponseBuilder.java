package tests.mocks;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.IResponseBuilder;
import bent.server.IRouter;

public class MockResponseBuilder implements IResponseBuilder {
    public HttpRequest buildResponseArgument;

    public HttpResponse buildResponse(HttpRequest request) {
        buildResponseArgument = request;
        return null;
    }

    public IRouter getRouter() {
        return new MockRouter();
    }
}
