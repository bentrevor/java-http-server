package bent.server;

import java.util.Arrays;

public class HttpRequest {
    public String requestLine = "";
    public String method = "";
    public String requestURI = "";
    public String httpVersion = "";
    public String accept = "";
    public String[] headers = null;
    public int contentLength = 0;

    public HttpRequest(String request) {
        requestLine = request.split("\r\n")[0];
        String[] fullHeaders = request.split("\r\n\r\n")[0].split("\r\n");

        headers = Arrays.copyOfRange(fullHeaders, 1, fullHeaders.length);

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
