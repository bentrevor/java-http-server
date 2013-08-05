package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class InputStreamTest {
    @Test
    public void itReadsTheFirstLine() throws IOException {
        InputStream stream = new ByteArrayInputStream("hi\r\n\r\n".getBytes());
        String result = new HttpReader().read(stream);
        assertEquals("hi", result);
    }

    @Test
    public void itReadsTwoLines() throws IOException {
        InputStream stream = new ByteArrayInputStream("hi\r\nbye\r\n\r\n".getBytes());
        String result = new HttpReader().read(stream);
        assertEquals("hi\r\nbye", result);
    }

    private class HttpReader{
        private char[] buffer = new char[255];
        int position = 0;

        public String read(InputStream stream) throws IOException {
            while(!done()){
                int nextChar = stream.read();
                buffer[position++] = (char) nextChar;
            }

            return new String(buffer).trim();
        }

        private boolean done(){
            return position > 3
                && '\n' == buffer[position-1]
                &&  '\r' == buffer[position-2]
                &&  '\n' == buffer[position-3]
                &&  '\r' == buffer[position-4]
            ;
        }
    }
}
