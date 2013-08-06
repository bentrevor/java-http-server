package bent.server;

public class HttpResponse {
    public StringBuilder response = null;
    public String httpVersion = "";
    public String statusCode = "";
    public String reasonPhrase = "";
    public String statusLine = "";
    public String contentLength = "";

    public HttpResponse() {
        response = new StringBuilder();
    }

    public void setHttpVersion(String version) {
        httpVersion = version;
        updateStatusLine();
    }

    public void setStatusCode(String code) {
        statusCode = code;
        updateStatusLine();
    }

    public void setReasonPhrase(String phrase) {
        reasonPhrase = phrase;
        updateStatusLine();
    }
    
    public void setContentLength(int length) {
        contentLength = Integer.toString(length);
    }

    public String toString() {
        response.append(statusLine);
        response.append("Content-Length: " + contentLength + "\r\n");
        response.append("\r\n");
        return response.toString();
    }

    private void updateStatusLine() {
        statusLine = httpVersion + " " + statusCode + " " + reasonPhrase + "\r\n";
    }
}
