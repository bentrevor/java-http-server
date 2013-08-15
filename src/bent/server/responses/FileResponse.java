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
        if (!request.getMethod().equals("GET")) {
            setStatusLine("HTTP/1.1 405 Method Not Allowed");
        } else {
            setStatusLine("HTTP/1.1 200 OK");
            setContentLength((int) file.length());
            String body = "";
            try {
                body = new String(readFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            setBody(body);
        }
    }

    private File openFile(String fileName) {
        return new File(System.getProperty("user.dir") + "/public" + fileName);
    }

    private byte[] readFile() throws IOException {
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        in.read(buffer);
        in.close();
        return buffer;
    }
}
