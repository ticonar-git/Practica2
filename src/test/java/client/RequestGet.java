package client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestGet implements IRequest{
    @Override
    public Response send(RequestInfo info) {
        return given()
                    .headers(info.getHeaders())
                    .log()
                    .all()
                .when()
                    .get(info.getUrl());
    }
}
