package tests.mocks;

import bent.server.HttpRequest;
import bent.server.HttpResponse;

public class MockHttpResponse extends HttpResponse {
    public int buildResponseCallCount;

    public MockHttpResponse() {
        buildResponseCallCount = 0;
    }

    public void buildResponse(HttpRequest request) {
        buildResponseCallCount++;
    }

    public byte[] bytes() {
        return new byte[0];
    }
}
