package tests;

import bent.server.CobSpecRouter;
import bent.server.HttpRequest;
import bent.server.IRouter;
import bent.server.ResponseBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockRouter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class ResponseBuilderTest {
    private ResponseBuilder builder;
    private HttpRequest request;
    private String response;

    @Before
    public void setUp() {
        IRouter router = new CobSpecRouter();
        builder = new ResponseBuilder(router);
    }

    @Test
    public void itBuildsASuccessResponse() {
        request = new HttpRequest("GET / HTTP/1.1\r\n\r\n");

        response = builder.buildResponse(request).toString();

        assertThat(response, containsString("HTTP/1.1 200 OK\r\n"));
    }

    @Test
    public void itBuildsAFailureResponse() {
        request = new HttpRequest("GET /foobar HTTP/1.1\r\n\r\n");

        response = builder.buildResponse(request).toString();

        assertThat(response, containsString("HTTP/1.1 404 Not Found\r\n"));
    }

    @Test
    public void itHandlesARedirectRoute() {
        request = new HttpRequest("GET /redirect HTTP/1.1\r\n\r\n");

        response = builder.buildResponse(request).toString();

        assertThat(response, containsString("HTTP/1.1 301 Moved Permanently\r\n"));
        assertThat(response, containsString("Location: http://localhost:5000/\r\n"));
    }

    @Test
    public void itHandlesPutRoute() {
        request = new HttpRequest("PUT /form HTTP/1.1\r\n\r\n\"My\"=\"Data\"");

        response = builder.buildResponse(request).toString();

        assertThat(response, containsString("HTTP/1.1 200 OK\r\n"));
    }

    @Test
    public void itHandlesFileRoutes() {
        request = new HttpRequest("GET /file1 HTTP/1.1\r\n\r\n");

        response = builder.buildResponse(request).toString();

        assertThat(response, containsString("HTTP/1.1 200 OK\r\n"));
        assertThat(response, containsString("file1 contents"));
    }

    @Test
    public void itSendsContentLengthWithImages() {
        request = new HttpRequest("GET /image.jpeg HTTP/1.1\r\n\r\n");

        response = builder.buildResponse(request).toString();

        assertThat(response, containsString("Content-Length: 38400"));
    }

    @Test
    public void itGetsRoutesFromARouterWhenConstructed() {
        MockRouter fakeRouter = new MockRouter();

        builder = new ResponseBuilder(fakeRouter);
        builder = new ResponseBuilder(fakeRouter);
        builder = new ResponseBuilder(fakeRouter);

        assertThat(fakeRouter.getRoutesCallCount, is(3));
    }
}
