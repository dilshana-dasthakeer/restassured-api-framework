package com.dilshana.api.client;

import static io.restassured.RestAssured.given;

import com.dilshana.api.config.Settings;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;

/**
 * The one place that builds a request. Two details matter for this API:
 * the content type must be plain application/json, with no charset added, and the Accept header must be
 * exactly application/json. REST-assured's defaults send more than that and the API answers 415 or 418.
 */
final class Requests {

    private static final RestAssuredConfig CONFIG = RestAssuredConfig.config().encoderConfig(
            EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false));

    private Requests() {
    }

    static RequestSpecification json() {
        return given()
                .config(CONFIG)
                .baseUri(Settings.BASE_URL)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }
}
