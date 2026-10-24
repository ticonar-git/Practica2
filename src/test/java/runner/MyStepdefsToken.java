package runner;

import client.FactoryRequest;
import client.RequestInfo;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import runner.config.Configuration;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class MyStepdefsToken {
    Response response;
    RequestInfo info;
    Map<String, String> variables = new HashMap<>();
    Map<String, String> headers = new HashMap<>();

    @Given("tengo acceso a Todo.ly con el usuario {string} y password {string}")
    public void tengoAccesoATodoLyConUsuarioYPassword(String email, String password) {
        // 1. Petición para obtener el Token usando Basic Auth limpiando cualquier auth previa
        Response authResponse = given()
                .auth().preemptive().basic(email, password)
                .when()
                .get(Configuration.BASE_URL + "api/authentication/token.json");

        authResponse.then().statusCode(200);

        // Imprimir la respuesta de autenticación para depurar
        System.out.println("Respuesta Auth JSON: " + authResponse.asString());

        // 2. Extraer el Token correctamente
        String token = authResponse.jsonPath().getString("TokenString"); // O prueba con "Token" si es el estándar

        // 3. Configurar la cabecera 'Token' requerida por Todo.ly (¡Ojo con el nombre de la cabecera!)
        // En Todo.ly la cabecera para el token suele llamarse "Token" o "TokenString" según la API wiki
        headers.clear();
        headers.put("Token", token);

        // También aseguramos el Token en Base64 si Todo.ly lo pide como Basic Auth alternativo,
        // pero con el token de API se pasa en el header "Token".
        System.out.println("Token extraído: " + token);
    }

    @When("send {} request {string} with")
    public void sendRequestWith(String method, String url, String body) {
        info = new RequestInfo(Configuration.BASE_URL + replaceVar(url),body);
        info.setHeaders(headers); // Se inyecta el Token en las cabeceras

        response = FactoryRequest.make(method).send(info);
    }

    @Then("code should be {int}")
    public void codeShouldBe(int expectedCode) {
        response.then()
                .statusCode(expectedCode);
    }

    @And("the {string} should be {string}")
    public void thenShouldBe(String attribute, String value) {
        response.then()
                .body(attribute, equalTo(value));
    }

    @And("save {string} in {string}")
    public void saveAttribute(String attribute, String variable) {
        int value = response.then().extract().path(attribute);
        variables.put(variable, value + "");
        System.out.println("Variable " + variable + " = " + value);
    }

    private String replaceVar(String value) {
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            value = value.replace(entry.getKey(), entry.getValue());
        }
        return value;
    }
}