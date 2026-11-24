package com.dilshana.api.client;

import com.dilshana.api.model.Booking;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

/**
 * One method per endpoint. Every method returns the raw Response, so a test decides what to assert
 * and the same client serves both happy-path and negative tests.
 */
public class BookingClient {

    private final String token;

    public BookingClient(String token) {
        this.token = token;
    }

    /** A client with no token, used to prove protected endpoints refuse anonymous callers. */
    public static BookingClient anonymous() {
        return new BookingClient(null);
    }

    public Response create(Booking booking) {
        return base().body(booking).when().post("/booking");
    }

    public Response get(int id) {
        return base().when().get("/booking/{id}", id);
    }

    public Response list(Map<String, ?> filters) {
        return base().queryParams(filters).when().get("/booking");
    }

    public Response update(int id, Booking booking) {
        return authed().body(booking).when().put("/booking/{id}", id);
    }

    public Response patch(int id, Map<String, ?> fields) {
        return authed().body(fields).when().patch("/booking/{id}", id);
    }

    public Response delete(int id) {
        return authed().when().delete("/booking/{id}", id);
    }

    private RequestSpecification base() {
        return Requests.json();
    }

    private RequestSpecification authed() {
        RequestSpecification spec = base();
        return token == null ? spec : spec.header("Cookie", "token=" + token);
    }
}
