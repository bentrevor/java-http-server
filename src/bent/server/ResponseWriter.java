package bent.server;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter implements IResponseWriter {
    public OutputStream outputStream;
    public String response;

    public void respondTo(HttpRequest request) throws IOException {
        response = buildResponse(request);
        sendResponse(response);
    }

    public String buildResponse(HttpRequest request) {
        HttpResponse response = new ResponseBuilder().buildResponse(request);

        return response.toString();
    }

    public void sendResponse(String response) throws IOException {
        outputStream.write(response.getBytes());
        outputStream.flush();
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
