package tests.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.responses.ParametersResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class ParametersResponseTest {
    @Test
    public void itReturns200Success() {
        HttpRequest request = new HttpRequest("GET /parameters HTTP/1.1\r\n\r\n");
        HttpResponse response = new ParametersResponse();

        response.buildResponse(request);

        assertThat(response.getStatusLine(), is("HTTP/1.1 200 OK"));
    }

    @Test
    public void itExtractsQueryStringParameters() {
        HttpRequest request = new HttpRequest("GET /parameters?key1=value1&key2=value2 HTTP/1.1\r\n\r\n");
        HttpResponse response = new ParametersResponse();

        response.buildResponse(request);

        assertThat(response.getStatusLine(), is("HTTP/1.1 200 OK"));
        assertThat(response.getStringBody(), containsString("key1 = value1"));
        assertThat(response.getStringBody(), containsString("key2 = value2"));
    }

    @Test
    public void itDecodesQueryStringParams() {
        HttpRequest request = new HttpRequest("GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff HTTP/1.1\r\n\r\n");
        HttpResponse response = new ParametersResponse();

        response.buildResponse(request);

        assertThat(response.getStatusLine(), is("HTTP/1.1 200 OK"));
        assertThat(response.getStringBody(), containsString("variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?"));
        assertThat(response.getStringBody(), containsString("variable_2 = stuff"));
    }
}
