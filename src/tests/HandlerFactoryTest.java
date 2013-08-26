package tests;

import bent.server.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockRouter;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class HandlerFactoryTest {
    private MockRouter fakeRouter;
    private HandlerFactory factory;

    @Before
    public void setUp() {
        fakeRouter = new MockRouter();
        factory = new HandlerFactory(fakeRouter);
    }

    @Test
    public void itCreatesARequestHandler() {
        RequestHandler rh = factory.makeHandler();

        assertThat(rh, is(instanceOf(RequestHandler.class)));
        assertThat(rh.getReader(), is(instanceOf(RequestReader.class)));
        assertThat(rh.getWriter(), is(instanceOf(ResponseWriter.class)));
        assertThat(rh.getBuilder(), is(instanceOf(ResponseBuilder.class)));
    }

    @Test
    public void itCreatesAFileSystemWithGivenRouter() {
        HandlerFactory factory = new HandlerFactory(fakeRouter);
        RequestHandler handler = factory.makeHandler();
        IResponseBuilder builder = handler.getBuilder();
        IRouter router = builder.getRouter();

        assertSame(fakeRouter, router);
    }
}
