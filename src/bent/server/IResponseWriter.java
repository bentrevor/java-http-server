package bent.server;

import java.io.IOException;

public interface IResponseWriter {
    public void respondTo(String request) throws IOException;
}
