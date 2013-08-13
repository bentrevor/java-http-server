package tests.responses;

import bent.server.CobSpecRouter;
import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.responses.FormResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Hashtable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class FormResponseTest {
    @Test
    public void itFails() {
        HttpRequest request = new HttpRequest("GET /form HTTP/1.1\r\n\r\n");
        HttpResponse response = new FormResponse();
        response.buildResponse(request);

        assertThat(response.statusLine, is("HTTP/1.1 200 OK"));
    }
}
