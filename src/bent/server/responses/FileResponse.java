package bent.server.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.IFileSystem;

import java.io.File;
import java.io.IOException;

public class FileResponse extends HttpResponse {
    private File file;
    private IFileSystem fileSystem;

    public FileResponse(IFileSystem files, String fileName) {
        fileSystem = files;
        file = fileSystem.open(fileName);
    }

    public void buildResponse(HttpRequest request) {
        if (!request.getMethod().equals("GET")) {
            setStatusLine("HTTP/1.1 405 Method Not Allowed");
        } else {
            setStatusLine("HTTP/1.1 200 OK");
            setImageContentType(request.getRequestURI());
            byte[] body = null;

            try {
                body = fileSystem.read(file);
                if (body != null) {
                    setContentLength(body.length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            String range = request.getRange();

            if (range != null) {
                body = trimBodyToRange(new String(body), range).getBytes();
                setStatusLine("HTTP/1.1 206 Partial Content");
            }
            setBody(body);
        }
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
