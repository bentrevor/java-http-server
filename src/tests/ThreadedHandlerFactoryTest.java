package tests;

import bent.server.ThreadedHandlerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockHandlerFactory;
import tests.mocks.MockSocket;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class ThreadedHandlerFactoryTest {
    @Test
    public void itUsesAHandlerFactoryToCreateThreadedHandlers() throws IOException {
        MockHandlerFactory nonThreadedFactory = new MockHandlerFactory();
        MockSocket fakeSocket = new MockSocket();
        ThreadedHandlerFactory threadedFactory = new ThreadedHandlerFactory(nonThreadedFactory);

        threadedFactory.makeHandler(fakeSocket);
        threadedFactory.makeHandler(fakeSocket);
        threadedFactory.makeHandler(fakeSocket);

        assertThat(nonThreadedFactory.makeHandlerCount, is(3));
    }
}
