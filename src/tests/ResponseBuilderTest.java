package tests;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.ResponseBuilder;
import bent.server.responses.NotFoundResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockRouter;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class ResponseBuilderTest {
    private ResponseBuilder builder;
    private HttpRequest request;
    private MockRouter fakeRouter;

    @Before
    public void setUp() {
        fakeRouter = new MockRouter();
        builder = new ResponseBuilder(fakeRouter);
    }

    @Test
    public void itGetsRoutesFromARouterWhenConstructed() {
        MockRouter fakeRouter = new MockRouter();

        builder = new ResponseBuilder(fakeRouter);
        builder = new ResponseBuilder(fakeRouter);
        builder = new ResponseBuilder(fakeRouter);

        assertThat(fakeRouter.getRoutesCallCount, is(3));
    }

    @Test
    public void itUsesNotFoundResponseForUnknownRoutes() {
        request = new HttpRequest("GET /foobar HTTP/1.1\r\n\r\n");

        HttpResponse response = builder.buildResponse(request);

        assertThat(response, is(instanceOf(NotFoundResponse.class)));
    }
}
