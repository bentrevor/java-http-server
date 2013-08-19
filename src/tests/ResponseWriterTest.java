package tests;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.ResponseWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

@RunWith(JUnit4.class)
public class ResponseWriterTest {
    private OutputStream outputStream;
    private ResponseWriter responder;

    @Before
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        responder = new ResponseWriter();
        responder.setOutputStream(outputStream);
    }

    @Test
    public void itWritesResponsesToItsOutputStream() throws IOException {
        HttpResponse response = new HttpResponse();
        response.setStatusLine("HTTP/1.1 200 OK");
        response.setContentLength(20);
        response.setBody("yo mama".getBytes());

        responder.send(response);

        String sentResponse = outputStream.toString();

        assertThat(sentResponse, containsString("HTTP/1.1 200 OK"));
        assertThat(sentResponse, containsString("Content-Length: 20"));
        assertThat(sentResponse, containsString("yo mama"));
    }
}
