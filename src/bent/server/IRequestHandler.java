package bent.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IRequestHandler extends Runnable {
    public void run();
    public void handleRequest();
    public void setReaderInputStream() throws IOException;
    public void setWriterOutputStream() throws IOException;
}
