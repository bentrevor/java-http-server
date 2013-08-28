package tests.mocks;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.IResponseBuilder;
import bent.server.IRouter;

public class MockResponseBuilder implements IResponseBuilder {
    public HttpRequest buildResponseArgument;
    public HttpResponse builtResponse;

    public HttpResponse buildResponse(HttpRequest request) {
        buildResponseArgument = request;
        return builtResponse;
    }

    public void setBuiltResponse(HttpResponse response) {
        builtResponse = response;
    }

    public IRouter getRouter() {
        return new MockRouter();
    }
}
