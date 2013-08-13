package tests;

import bent.server.CobSpecRouter;
import bent.server.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Hashtable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class CobSpecRouterTest {
    @Test
    public void itHasTheRootRoute() {
        CobSpecRouter router = new CobSpecRouter();
        Hashtable<String, HttpResponse> routes = router.getRoutes();
        HttpResponse response = routes.get("/");

        assertThat(response, is(notNullValue()));
    }
}
