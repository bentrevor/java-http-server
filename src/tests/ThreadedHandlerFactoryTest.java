package tests;

import bent.server.HandlerFactory;
import bent.server.IHandlerFactory;
import bent.server.ThreadedHandlerFactory;
import bent.server.ThreadedRequestHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockHandlerFactory;
import tests.mocks.MockRouter;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class ThreadedHandlerFactoryTest {
    @Test
    public void itUsesAHandlerFactoryToCreateThreadedHandlers() {
        MockHandlerFactory nonThreadedFactory = new MockHandlerFactory();
        ThreadedHandlerFactory threadedFactory = new ThreadedHandlerFactory(nonThreadedFactory);

        threadedFactory.makeHandler();
        threadedFactory.makeHandler();
        threadedFactory.makeHandler();

        assertThat(nonThreadedFactory.makeHandlerCount, is(3));
    }
}
