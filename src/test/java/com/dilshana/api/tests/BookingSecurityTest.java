package com.dilshana.api.tests;

import static org.assertj.core.api.Assertions.assertThat;

import com.dilshana.api.client.AuthClient;
import com.dilshana.api.client.BookingClient;
import com.dilshana.api.data.BookingFactory;
import com.dilshana.api.model.CreatedBooking;
import java.util.Map;
import org.testng.annotations.Test;

/** Negative and security cases: who may change data, and how the API treats bad input. */
public class BookingSecurityTest extends BaseApiTest {

    @Test(groups = {"smoke", "regression"})
    public void wrongCredentialsDoNotReturnAToken() {
        String reason = new AuthClient().login("admin", "not-the-password")
                .then().statusCode(200).extract().path("reason");

        assertThat(reason).isEqualTo("Bad credentials");
    }

    @Test(groups = "regression")
    public void updateWithoutATokenIsForbidden() {
        CreatedBooking created = createBooking(BookingFactory.valid());

        BookingClient.anonymous().update(created.bookingid(), BookingFactory.valid())
                .then().statusCode(403);
    }

    @Test(groups = "regression")
    public void deleteWithoutATokenIsForbidden() {
        CreatedBooking created = createBooking(BookingFactory.valid());

        BookingClient.anonymous().delete(created.bookingid()).then().statusCode(403);

        client.get(created.bookingid()).then().statusCode(200);
    }

    @Test(groups = "regression")
    public void unknownBookingIsNotFound() {
        client.get(999_999_999).then().statusCode(404);
    }

    @Test(groups = "regression")
    public void createWithAMissingRequiredFieldIsRejected() {
        // The API answers 500 for an incomplete body instead of a 4xx. The test pins the current behaviour
        // and says so, so a change in either direction is noticed.
        client.create(new com.dilshana.api.model.Booking(null, null, 0, false, null, null))
                .then().statusCode(500);
    }

    @Test(groups = "regression")
    public void patchWithoutATokenIsForbidden() {
        CreatedBooking created = createBooking(BookingFactory.valid());

        BookingClient.anonymous().patch(created.bookingid(), Map.of("totalprice", 1))
                .then().statusCode(403);
    }
}
