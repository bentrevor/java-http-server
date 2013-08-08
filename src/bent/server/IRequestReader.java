package bent.server;

import java.io.IOException;

public interface IRequestReader {
    public String readFromSocket() throws IOException;
}
