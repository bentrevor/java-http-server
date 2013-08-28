package tests.mocks;

import bent.server.IFileManager;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class MockFileManager implements IFileManager {
    public int openFileCallCount = 0;
    public int readFileCallCount = 0;
    public String openFileArgument;
    private Hashtable<String, byte[]> files;

    public MockFileManager() {
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

    public File getPublicDirectory() {
        return new File("mockroot/public");
    }

    public String[] getPublicFilenames() {
        String[] filenames = new String[files.size()];
        int index = 0;

        for (String filename : files.keySet()) {
            filenames[index] = filename;
            index++;
        }

        return filenames;
    }
}
