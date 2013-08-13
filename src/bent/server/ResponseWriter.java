package bent.server;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter implements IResponseWriter {
    private OutputStream outputStream;

    public void send(HttpResponse response) throws IOException {
        outputStream.write(response.toString().getBytes());
    }

    public void setOutputStream(OutputStream out) {
        outputStream = out;
    }
}
