package tests;

import bent.server.CobSpecRouter;
import bent.server.HttpResponse;
import bent.server.responses.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tests.mocks.MockFileSystem;

import java.util.Hashtable;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class CobSpecRouterTest {
    private Hashtable<String, HttpResponse> routes;
    private MockFileSystem fakeFileSystem;

    @Before
    public void setUp() {
        fakeFileSystem = new MockFileSystem();
        CobSpecRouter router = new CobSpecRouter(fakeFileSystem);
        routes = router.getRoutes();
    }

    @Test
    public void itHasTheRootRoute() {
        HttpResponse response = routes.get("/");

        assertThat(response, is(notNullValue()));
        assertThat(response, is(instanceOf(RootResponse.class)));
    }

    @Test
    public void itHasTheRedirectRoute() {
        HttpResponse response = routes.get("/redirect");

        assertThat(response, is(notNullValue()));
        assertThat(response, is(instanceOf(RedirectResponse.class)));
    }

    @Test
    public void itHasTheFormRoute() {
        HttpResponse response = routes.get("/form");

        assertThat(response, is(notNullValue()));
        assertThat(response, is(instanceOf(FormResponse.class)));
    }

    @Test
    public void itHasTheFile2Route() {
        HttpResponse response = routes.get("/file2");

        assertThat(response, is(notNullValue()));
        assertThat(response, is(instanceOf(FileResponse.class)));
    }

    @Test
    public void itHasImageRoutes() {
        HttpResponse jpegResponse = routes.get("/image.jpeg");
        HttpResponse pngResponse = routes.get("/image.png");
        HttpResponse gifResponse = routes.get("/image.gif");

        assertThat(jpegResponse, is(notNullValue()));
        assertThat(jpegResponse, is(instanceOf(FileResponse.class)));
        assertThat(gifResponse, is(notNullValue()));
        assertThat(gifResponse, is(instanceOf(FileResponse.class)));
        assertThat(pngResponse, is(notNullValue()));
        assertThat(pngResponse, is(instanceOf(FileResponse.class)));
    }

    @Test
    public void itHasRouteForTextFile() {
        HttpResponse response = routes.get("/text-file.txt");

        assertThat(response, is(notNullValue()));
        assertThat(response, is(instanceOf(FileResponse.class)));
    }

    @Test
    public void itHasRouteForPartialContent() {
        HttpResponse response = routes.get("/partial_content.txt");

        assertThat(response, is(notNullValue()));
        assertThat(response, is(instanceOf(FileResponse.class)));
    }

    @Test
    public void itHasRouteForParameters() {
        HttpResponse response = routes.get("/parameters");

        assertThat(response, is(notNullValue()));
        assertThat(response, is(instanceOf(ParametersResponse.class)));
    }

    @Test
    public void itUsesTheInjectedFilesystem() {
        assertThat(fakeFileSystem.openFileCallCount, is(7));
    }
}
