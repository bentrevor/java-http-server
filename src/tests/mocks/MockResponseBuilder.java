package tests.mocks;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.IResponseBuilder;

public class MockResponseBuilder implements IResponseBuilder {
    public HttpRequest buildResponseArgument;

    public HttpResponse buildResponse(HttpRequest request) {
        buildResponseArgument = request;
        return new HttpResponse();
    }
}
