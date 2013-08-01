package bent.server;

import java.util.Hashtable;

public class HttpRequest extends Hashtable {
    public String method = "";
    public String requestURI = "";
    public String httpVersion = "";

    public String put(String key, String value) {
        if (key.equals("Method")) {
            method = value;
        } else if (key.equals("Request-URI")) {
            requestURI = value;
        } else if (key.equals("HTTP-Version")) {
            httpVersion = value;
        }

        return value;
    }
}
