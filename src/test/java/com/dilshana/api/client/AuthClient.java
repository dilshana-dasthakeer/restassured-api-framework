package com.dilshana.api.client;

import static io.restassured.RestAssured.given;

import com.dilshana.api.config.Settings;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Map;

public class AuthClient {

    /** Asks for a token and returns the raw response, so tests can check failures too. */
    public Response login(String username, String password) {
        return given()
                .baseUri(Settings.BASE_URL)
                .contentType(ContentType.JSON)
                .body(Map.of("username", username, "password", password))
                .when()
                .post("/auth");
    }

    public String validToken() {
        return login(Settings.USERNAME, Settings.PASSWORD).then().statusCode(200).extract().path("token");
    }
}
