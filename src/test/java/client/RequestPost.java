package client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestPost implements IRequest {
    @Override
    public Response send(RequestInfo info) {
        return given()
                    .headers(info.getHeaders())        // <-- ¡Obligatorio para que viaje el Token!
                    .contentType("application/json")   // <-- ¡Obligatorio para que sea JSON y no text/plain!
                    .body(info.getBody())
                    .log()
                    .all()
                .when()
                    .post(info.getUrl());
    }
}
