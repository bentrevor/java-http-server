package bent.server;

import java.io.File;
import java.io.IOException;

public interface IFileSystem {
    public File open(String fileName);
    public byte[] read(File file) throws IOException;
    public String getPublicDirectory();
}
