package bent.server.responses;

import bent.server.HttpRequest;
import bent.server.HttpResponse;

public class ParametersResponse extends HttpResponse {
    public void buildResponse(HttpRequest request) {
        setStatusLine("HTTP/1.1 200 OK");

        extractQueryStringParameters(request);
    }

    private void extractQueryStringParameters(HttpRequest request) {
        String body = "";
        String[] params = request.queryStringParams.split("&");

        for (String param : params) {
            param = param.replaceFirst("=", " = ");
            param = param.replaceAll("%20", " ");
            param = param.replaceAll("%2C", ",");
            param = param.replaceAll("%3C", "<");
            param = param.replaceAll("%3E", ">");
            param = param.replaceAll("%3D", "=");
            param = param.replaceAll("%3F", "?");
            param = param.replaceAll("%3B", ";");
            param = param.replaceAll("%3A", ":");
            param = param.replaceAll("%2B", "+");
            param = param.replaceAll("%26", "&");
            param = param.replaceAll("%40", "@");
            param = param.replaceAll("%22", "\\\"");
            param = param.replaceAll("%23", "#");
            param = param.replaceAll("%24", "\\$");
            param = param.replaceAll("%5B", "[");
            param = param.replaceAll("%5D", "]");

            body += param;
        }

        setBody(body);
    }
}
