package bent.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface IFileManager {
    public byte[] read(Path path) throws IOException;
    public File getPublicDirectory();
    public String[] getPublicFilenames();
}
