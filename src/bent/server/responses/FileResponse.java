package bent.server.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;
import bent.server.IFileManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileResponse extends HttpResponse {
    private IFileManager fileManager;
    private Path filePath;

    public FileResponse(IFileManager files, String fileName) {
        fileManager = files;
        filePath = Paths.get(files.getPublicDirectory() + fileName);
    }

    public void buildResponse(HttpRequest request) {
        if (!request.getMethod().equals("GET")) {
            setStatusLine("HTTP/1.1 405 Method Not Allowed");
        } else {
            setStatusLine("HTTP/1.1 200 OK");
            setImageContentType(request.getRequestURI());
            byte[] body = null;

            try {
                body = fileManager.read(filePath);
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

    public Path getFilePath() {
        return filePath;
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
