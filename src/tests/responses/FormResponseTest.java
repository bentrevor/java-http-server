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
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class FormResponseTest {
        HttpRequest request;
        HttpResponse response;

    @Test
    public void itReturns200Status() {
        request = new HttpRequest("GET /form HTTP/1.1\r\n\r\n");
        response = new FormResponse();
        response.buildResponse(request);

        assertThat(response.statusLine, is("HTTP/1.1 200 OK"));
    }

    @Test
    public void itStartsWithAnEmptyBody() {
        request = new HttpRequest("GET /form HTTP/1.1\r\n\r\n");
        response = new FormResponse();
        response.buildResponse(request);

        assertThat(response.body, is(""));
    }

    @Test
    public void itSavesDataFromPostRequestsToTheBody() {
        request = new HttpRequest("POST /form HTTP/1.1\r\nContent-Length: 10\r\n\r\ndata=cosby");
        response = new FormResponse();
        response.buildResponse(request);

        assertThat(response.body, containsString("data = cosby"));
    }
}
