package bent.server.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileResponse extends HttpResponse {
    private File file;

    public FileResponse(String fileName) throws FileNotFoundException {
        file = createFile(fileName);
    }

    public void buildResponse(HttpRequest request) {
        if (!request.getMethod().equals("GET")) {
            setStatusLine("HTTP/1.1 405 Method Not Allowed");
        } else {
            setStatusLine("HTTP/1.1 200 OK");
            setContentLength((int) file.length());
            setImageContentType(request.getRequestURI());
            byte[] body = null;
            try {
                body = readFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String range = request.getRange();

            if (!(range == null)) {
                body = trimBodyToRange(new String(body), range).getBytes();
                setStatusLine("HTTP/1.1 206 Partial Content");
            }
            setBody(body);
        }
    }

    private File createFile(String fileName) {
        return new File(System.getProperty("user.dir") + "/public" + fileName);
    }

    private byte[] readFile() throws IOException {
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        in.read(buffer);
        in.close();
        return buffer;
    }

    private void setImageContentType(String uri) {
        if (uri.contains("image.")) {
            setContentType("image/" + uri.split("\\.")[1]);
        }
    }

    private String trimBodyToRange(String body, String range) {
        int start = Integer.parseInt(range.split("=")[1].split("-")[0]);
        int end = Integer.parseInt(range.split("=")[1].split("-")[1]);
        return body.substring(start, end);
    }
}
