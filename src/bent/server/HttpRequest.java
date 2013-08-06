package bent.server;

public class HttpRequest {
    public String requestLine;
    public String method;
    public String requestURI;
    public String httpVersion;
    public String accept;
    public String[] headers;
    public int contentLength;

    public HttpRequest(String request) {
        requestLine = request.split("\r\n")[0];
        headers = request.split("\r\n", 2)[1].split("\r\n");

        method = requestLine.split(" ")[0];
        requestURI = requestLine.split(" ")[1];
        httpVersion = requestLine.split(" ")[2];

        for (String header : headers) {
            String headerName = header.split(":")[0].toLowerCase();
            String value = header.split(":")[1].trim();

            switch (headerName) {
                case "content-length":
                    contentLength = Integer.parseInt(value);
                    break;
                case "accept":
                    accept = value;
                    break;
            }
        }
    }
}
