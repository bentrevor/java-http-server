package tests;

import bent.server.FileSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class FileSystemTest {
    @Test
    public void itSetsTheRootDirectoryInConstructor() {
        FileSystem fs = new FileSystem("path/to/public");

        assertThat(fs.getPublicDirectory(), is("path/to/public"));
    }
}
