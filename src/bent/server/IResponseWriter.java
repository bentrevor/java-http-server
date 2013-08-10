package bent.server;

import java.io.IOException;
import java.io.OutputStream;

public interface IResponseWriter {
    public void respondTo(HttpRequest request) throws IOException;
    public void setOutputStream(OutputStream stream);
}
