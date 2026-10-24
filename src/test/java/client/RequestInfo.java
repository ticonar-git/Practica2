package client;

import java.util.Map;

public class RequestInfo {
    private String url;
    private String body;
    private Map<String, String> headers; // <-- 1. Añadir el campo

    public RequestInfo(String url, String body) {
        this.url = url;
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers; // <-- 2. Guardar el valor
    }

    public Map<String,?> getHeaders() {
        return headers; // <-- 3. Retornar el valor correctamente
    }
}
