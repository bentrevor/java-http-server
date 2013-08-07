package bent.server;

public class HttpRequest {
    public String requestLine;
    public String method;
    public String requestURI;
    public String httpVersion;
    public String accept;
    public String[] headers;
    public int contentLength;
    public String body;

    public HttpRequest(String request) {
        body = null;
        headers = new String[0];
        String fullHeaders = request.split("\r\n\r\n")[0];
        requestLine = fullHeaders.split("\r\n", 2)[0];

        method = requestLine.split(" ")[0];
        requestURI = requestLine.split(" ")[1];
        httpVersion = requestLine.split(" ")[2];

        if (fullHeaders.split("\r\n").length > 1) {
            headers = fullHeaders.split("\r\n", 2)[1].split("\r\n");

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
}
