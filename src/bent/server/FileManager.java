package bent.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager implements IFileManager {
    private String rootDirectory;

    public FileManager(String root) {
        rootDirectory = root;
    }

    public File open(String fileName) {
        return new File(getPublicDirectory() + fileName);
    }

    public byte[] read(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        in.read(buffer);
        in.close();
        return buffer;
    }

    public byte[] read(Path path) throws IOException {
        return Files.readAllBytes(path);
    }

    public File getPublicDirectory() {
        return new File(rootDirectory + "/public");
    }

    public String[] getPublicFilenames() {
        return getPublicDirectory().list();
    }
}
