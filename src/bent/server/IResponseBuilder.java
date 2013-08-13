package bent.server;

public interface IResponseBuilder {
    public HttpResponse buildResponse(HttpRequest request);
}
