package com.dilshana.api.tests;

import static org.assertj.core.api.Assertions.assertThat;

import com.dilshana.api.data.BookingFactory;
import com.dilshana.api.model.Booking;
import com.dilshana.api.model.CreatedBooking;
import java.util.Map;
import org.testng.annotations.Test;

/** Create, read, change and remove a booking: the core business flow of the API. */
public class BookingLifecycleTest extends BaseApiTest {

    @Test(groups = {"smoke", "regression"})
    public void createdBookingCanBeReadBack() {
        Booking booking = BookingFactory.valid();

        CreatedBooking created = createBooking(booking);
        Booking stored = client.get(created.bookingid()).then().statusCode(200).extract().as(Booking.class);

        assertThat(created.bookingid()).isPositive();
        assertThat(stored).isEqualTo(booking);
    }

    @Test(groups = "regression")
    public void fullUpdateReplacesEveryField() {
        CreatedBooking created = createBooking(BookingFactory.valid());
        Booking replacement = BookingFactory.withName(BookingFactory.uniqueName());

        Booking updated = client.update(created.bookingid(), replacement)
                .then().statusCode(200).extract().as(Booking.class);

        assertThat(updated).isEqualTo(replacement);
        assertThat(client.get(created.bookingid()).as(Booking.class)).isEqualTo(replacement);
    }

    @Test(groups = "regression")
    public void partialUpdateChangesOnlyTheGivenFields() {
        Booking original = BookingFactory.valid();
        CreatedBooking created = createBooking(original);

        Booking patched = client.patch(created.bookingid(), Map.of("totalprice", 999, "additionalneeds", "Parking"))
                .then().statusCode(200).extract().as(Booking.class);

        assertThat(patched.totalprice()).isEqualTo(999);
        assertThat(patched.additionalneeds()).isEqualTo("Parking");
        assertThat(patched.firstname()).isEqualTo(original.firstname());
        assertThat(patched.bookingdates()).isEqualTo(original.bookingdates());
    }

    @Test(groups = "regression")
    public void deletedBookingIsGone() {
        CreatedBooking created = createBooking(BookingFactory.valid());

        client.delete(created.bookingid()).then().statusCode(201);

        client.get(created.bookingid()).then().statusCode(404);
    }
}
