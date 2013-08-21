package tests;

import bent.server.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class HandlerFactoryTest {
    @Test
    public void itCreatesARequestHandler() {
        HandlerFactory factory = new HandlerFactory("");

        RequestHandler rh = factory.makeHandler();

        assertThat(rh.getReader(), is(instanceOf(RequestReader.class)));
        assertThat(rh.getWriter(), is(instanceOf(ResponseWriter.class)));
        assertThat(rh.getBuilder(), is(instanceOf(ResponseBuilder.class)));
    }

    @Test
    public void itKnowsThePathToThePublicDirectory() {
        HandlerFactory factory = new HandlerFactory("path/to/public");

        assertThat(factory.getPublicDir(), is("path/to/public"));
    }

    @Test
    public void itCreatesAFileSystemWithGivenPublicDirectory() {
        HandlerFactory factory = new HandlerFactory("path/to/public");
        RequestHandler handler = factory.makeHandler();
        IResponseBuilder builder = handler.getBuilder();
        IRouter router = builder.getRouter();
        IFileSystem fileSystem = router.getFileSystem();

        assertThat(fileSystem.getPublicDirectory(), is("path/to/public"));
    }
}
