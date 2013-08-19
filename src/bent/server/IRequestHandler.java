package bent.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IRequestHandler {
    public void handleRequest() throws IOException;
    public void setReaderInputStream(InputStream in);
    public void setWriterOutputStream(OutputStream out);
}
