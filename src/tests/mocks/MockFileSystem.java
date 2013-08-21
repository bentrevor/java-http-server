package tests.mocks;

import bent.server.IFileSystem;

import java.io.File;
import java.util.Hashtable;

public class MockFileSystem implements IFileSystem {
    public int openFileCallCount = 0;
    public int readFileCallCount = 0;
    public String openFileArgument;
    private Hashtable<String, byte[]> files;

    public MockFileSystem() {
        openFileCallCount = 0;
        readFileCallCount = 0;
        openFileArgument = "";
        files = new Hashtable<>();
    }

    public File open(String fileName) {
        openFileCallCount++;
        openFileArgument = fileName;
        return new File(fileName);
    }

    public byte[] read(File file) {
        readFileCallCount++;
        return files.get(file.getName());
    }

    public void addFile(String fileName, String fileContents) {
        files.put(fileName, fileContents.getBytes());
    }

    public String getPublicDirectory() {
        return "path/to/MockFileSystem/public";
    }
}