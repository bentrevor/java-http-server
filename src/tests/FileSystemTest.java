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
public class FileSystemTest {
    @Test
    public void itSavesThePublicDirectoryAsAFile() {
        FileManager fs = new FileManager("rootpath");

        assertThat(fs.getPublicDirectory(), is(instanceOf(File.class)));
        assertThat(fs.getPublicDirectory().getPath(), is("rootpath/public"));
    }
}
