package tests.mocks;

import bent.server.IFileManager;

import java.io.File;
import java.nio.file.Path;
import java.util.Hashtable;

public class MockFileManager implements IFileManager {
    public int readFilePathCallCount;
    public Path readFilePathArgument;
    public Hashtable<String, byte[]> files;

    public MockFileManager() {
        readFilePathCallCount = 0;
        files = new Hashtable<>();
    }

    public byte[] read(Path path) {
        readFilePathCallCount++;
        readFilePathArgument = path;
        String[] splitPath = path.toString().split("/");
        String fileName = "/" + splitPath[splitPath.length - 1];
        return files.get(fileName);
    }

    public void addFile(String fileName, String fileContents) {
        files.put("/" + fileName, fileContents.getBytes());
    }

    public File getPublicDirectory() {
        return new File("mockroot/public");
    }

    public String[] getPublicFilenames() {
        String[] filenames = new String[files.size()];
        int index = 0;

        for (String filename : files.keySet()) {
            filenames[index] = stripLeadingSlash(filename);
            index++;
        }

        return filenames;
    }

    private String stripLeadingSlash(String fileName) {
        return fileName.substring(1);
    }
}
