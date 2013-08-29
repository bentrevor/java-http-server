package tests.mocks;

import bent.server.IFileManager;

import java.io.File;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class MockFileManager implements IFileManager {
    public int openFileCallCount;
    public int readFileCallCount;
    public int readFilePathCallCount;
    public Path readFilePathArgument;
    public String openFileArgument;
    public Hashtable<String, byte[]> files;

    public MockFileManager() {
        openFileCallCount = 0;
        readFileCallCount = 0;
        readFilePathCallCount = 0;
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
