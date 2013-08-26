package tests.mocks;

import bent.server.HttpResponse;
import bent.server.IFileManager;
import bent.server.IRouter;

import java.util.Hashtable;

public class MockRouter implements IRouter {
    public int getRoutesCallCount;
    private Hashtable<String, HttpResponse> routes;

    public MockRouter() {
        getRoutesCallCount = 0;
        routes = new Hashtable<>();
    }

    public Hashtable<String, HttpResponse> getRoutes() {
        getRoutesCallCount++;
        return routes;
    }

    public IFileManager getFileSystem() {
        return new MockFileManager();
    }
}
