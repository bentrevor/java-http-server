package tests;

import bent.server.CobSpecRouter;
import bent.server.HttpResponse;
import bent.server.responses.FileResponse;
import bent.server.responses.FormResponse;
import bent.server.responses.RedirectResponse;
import bent.server.responses.RootResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Hashtable;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class CobSpecRouterTest {
    public CobSpecRouter router;
    public Hashtable<String, HttpResponse> routes;

    @Before
    public void setUp() {
        router = new CobSpecRouter();
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
    public void itHasTheFileRoute() {
        HttpResponse response = routes.get("/file1");

        assertThat(response, is(notNullValue()));
        assertThat(response, is(instanceOf(FileResponse.class)));
    }
}
