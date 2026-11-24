package com.dilshana.api.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.dilshana.api.data.BookingFactory;
import com.dilshana.api.model.CreatedBooking;
import org.testng.annotations.Test;

/** Checks the shape of the response, so a renamed or retyped field is caught even if values still look right. */
public class BookingContractTest extends BaseApiTest {

    @Test(groups = {"smoke", "regression"})
    public void bookingResponseMatchesTheSchema() {
        CreatedBooking created = createBooking(BookingFactory.valid());

        client.get(created.bookingid())
                .then().statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/booking-schema.json"));
    }
}
