package bent.server;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter implements IResponseWriter {
    private OutputStream outputStream;

    public void respondTo(HttpRequest request) throws IOException {
        String response = buildResponse(request);
        sendResponse(response);
    }

    String buildResponse(HttpRequest request) {
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
