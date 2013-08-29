package tests;

import bent.server.FileManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class FileManagerTest {
    FileManager fileManager;

    @Test
    public void itSavesThePublicDirectoryAsAFile() {
        fileManager = new FileManager("rootpath");

        File publicDir = fileManager.getPublicDirectory();

        assertThat(publicDir, is(instanceOf(File.class)));
        assertThat(publicDir.getPath(), is("rootpath/public"));
    }

    @Test
    public void itOpensFilesFromPublicDirectory() {
        fileManager = new FileManager("rootpath");

        File someFile = fileManager.open("/some_file");

        assertThat(someFile.getPath(), is("rootpath/public/some_file"));
    }
}
