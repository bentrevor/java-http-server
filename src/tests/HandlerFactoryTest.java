package tests;

import bent.server.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockRouter;
import tests.mocks.MockSocket;

import java.io.IOException;

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
    public void itCreatesARequestHandler() throws IOException {
        MockSocket fakeSocket = new MockSocket();
        RequestHandler rh = factory.makeHandler(fakeSocket);

        assertThat(rh, is(instanceOf(RequestHandler.class)));
        assertThat(rh.getReader(), is(instanceOf(RequestReader.class)));
        assertThat(rh.getWriter(), is(instanceOf(ResponseWriter.class)));
        assertThat(rh.getBuilder(), is(instanceOf(ResponseBuilder.class)));
        assertSame(rh.getSocket(), fakeSocket);
    }

    @Test
    public void itCreatesAFileSystemWithGivenRouter() throws IOException {
        HandlerFactory factory = new HandlerFactory(fakeRouter);
        RequestHandler handler = factory.makeHandler(new MockSocket());
        IResponseBuilder builder = handler.getBuilder();
        IRouter router = builder.getRouter();

        assertSame(fakeRouter, router);
    }
}
