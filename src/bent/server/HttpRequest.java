package bent.server;

public class HttpRequest {
    public String method = "";
    public String requestURI = "";
    public String httpVersion = "";
    public String headers = "";

    public HttpRequest(String request) {
        headers = request.split("\r\n\r\n")[0];
        method = headers.split(" ")[0];
        requestURI = headers.split(" ")[1];
        httpVersion = headers.split(" ")[2];
    }
}
