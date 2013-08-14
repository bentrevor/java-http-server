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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class FormResponseTest {
        HttpRequest request;
        HttpResponse response;

    @Before
    public void setUp() {
        response = new FormResponse();
    }

    @Test
    public void itReturns200Status() {
        request = new HttpRequest("GET /form HTTP/1.1\r\n\r\n");
        response.buildResponse(request);

        assertThat(response.statusLine, is("HTTP/1.1 200 OK"));
    }

    @Test
    public void itStartsWithAnEmptyBody() {
        request = new HttpRequest("GET /form HTTP/1.1\r\n\r\n");
        response.buildResponse(request);

        assertThat(response.body, is(""));
    }

    @Test
    public void itSavesDataFromPostRequestsToTheBody() {
        request = new HttpRequest("POST /form HTTP/1.1\r\nContent-Length: 10\r\n\r\ndata=cosby");
        response.buildResponse(request);

        assertThat(response.body, containsString("data = cosby"));
    }

    @Test
    public void itSavesDataFromPutRequestsToTheBody() {
        request = new HttpRequest("PUT /form HTTP/1.1\r\nContent-Length: 10\r\n\r\ndata=bubsy");
        response.buildResponse(request);

        assertThat(response.body, containsString("data = bubsy"));
    }


    @Test
    public void itKeepsTheBodyBetweenRequests() {
        request = new HttpRequest("POST /form HTTP/1.1\r\nContent-Length: 10\r\n\r\ndata=cosby");
        response.buildResponse(request);

        request = new HttpRequest("GET /form HTTP/1.1\r\n\r\n");
        response.buildResponse(request);

        assertThat(response.body, containsString("data = cosby"));
    }
}
