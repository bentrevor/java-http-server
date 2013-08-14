package tests.mocks;

import bent.server.HttpResponse;
import bent.server.IRouter;

import java.util.Hashtable;

public class MockRouter implements IRouter {
    public int getRoutesCallCount;

    public MockRouter() {
        getRoutesCallCount = 0;
    }

    public Hashtable<String, HttpResponse> getRoutes() {
        getRoutesCallCount++;
        return new Hashtable<>();
    }
}