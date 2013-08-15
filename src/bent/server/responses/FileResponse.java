package bent.server.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;

import java.io.*;

public class FileResponse extends HttpResponse {
    private File file;

    public FileResponse(String fileName) throws FileNotFoundException {
        file = openFile(fileName);
    }

    public void buildResponse(HttpRequest request) {
        setStatusLine("HTTP/1.1 200 OK");
        String body = "";
        try {
            body = new String(readFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBody(body);
    }

    private File openFile(String fileName) {
        return new File(System.getProperty("user.dir") + "/public" + fileName);
    }

    private byte[] readFile() throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[2048];
        in.read(buffer);
        in.close();
        return buffer;
    }
}
