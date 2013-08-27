package tests;

import bent.server.ThreadedRequestHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockExecutorService;
import tests.mocks.MockRequestHandler;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class ThreadedRequestHandlerTest {

    @Test
    public void itUsesAnExecutorToRunARequestHandler() {
        MockRequestHandler fakeRequestHandler = new MockRequestHandler();
        MockExecutorService fakeExecutorService = new MockExecutorService();
        ThreadedRequestHandler handler = new ThreadedRequestHandler(fakeRequestHandler, fakeExecutorService);

        handler.handleRequest();
        handler.handleRequest();
        handler.handleRequest();

        assertThat(fakeExecutorService.executeCallCount, is(3));
        assertSame(fakeExecutorService.executeArgument, fakeRequestHandler);
    }
}
