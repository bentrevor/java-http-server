package tests;

import bent.server.HandlerFactory;
import bent.server.IRequestHandler;
import bent.server.ThreadedHandlerFactory;
import bent.server.ThreadedRequestHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockExecutorService;
import tests.mocks.MockHandlerFactory;
import tests.mocks.MockRouter;
import tests.mocks.MockSocket;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class ThreadedHandlerFactoryTest {
    @Test
    public void itUsesAHandlerFactoryToCreateHandlers() throws IOException {
        MockExecutorService fakeExecutor = new MockExecutorService();
        MockHandlerFactory nonThreadedFactory = new MockHandlerFactory();
        MockSocket fakeSocket = new MockSocket();
        ThreadedHandlerFactory threadedFactory = new ThreadedHandlerFactory(nonThreadedFactory, fakeExecutor);

        threadedFactory.makeHandler(fakeSocket);
        threadedFactory.makeHandler(fakeSocket);
        threadedFactory.makeHandler(fakeSocket);

        assertThat(nonThreadedFactory.makeHandlerCount, is(3));
    }

    @Test
    public void itCreatesAThreadedHandler() throws IOException {
        MockExecutorService fakeExecutor = new MockExecutorService();
        HandlerFactory nonThreadedFactory = new HandlerFactory(new MockRouter());
        MockSocket fakeSocket = new MockSocket();
        ThreadedHandlerFactory threadedFactory = new ThreadedHandlerFactory(nonThreadedFactory, fakeExecutor);

        ThreadedRequestHandler createdHandler = (ThreadedRequestHandler) threadedFactory.makeHandler(fakeSocket);

        assertThat(createdHandler, is(instanceOf(ThreadedRequestHandler.class)));
        assertSame(createdHandler.getExecutor(), fakeExecutor);
    }
}

