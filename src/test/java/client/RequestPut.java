package client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestPut implements IRequest {
    @Override
    public Response send(RequestInfo info) {
        return given()
                    .headers(info.getHeaders())
                    .contentType("application/json")   // <-- ¡Obligatorio para el PUT!
                    .body(info.getBody())              // <-- ¡Obligatorio para enviar el nuevo nombre del proyecto!
                    .log()
                    .all()
                .when()
                    .put(info.getUrl());
    }
}
