package bent.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileSystem implements IFileSystem {
    private String rootDir;

    public FileSystem(String root) {
        rootDir = root;
    }

    public File open(String fileName) {
        return new File(rootDir + fileName);
    }

    public byte[] read(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        in.read(buffer);
        in.close();
        return buffer;
    }

    public String getRootDir() {
        return rootDir;
    }
}
