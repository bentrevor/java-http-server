package bent.server;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter implements IResponseWriter {
    private OutputStream outputStream;

    public void send(HttpResponse response) throws IOException {
        outputStream.write(response.bytes());
    }

    public void setOutputStream(OutputStream out) {
        outputStream = out;
    }
}
