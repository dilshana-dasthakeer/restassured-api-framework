package com.dilshana.api.tests;

import com.dilshana.api.client.AuthClient;
import com.dilshana.api.client.BookingClient;
import com.dilshana.api.model.Booking;
import com.dilshana.api.model.CreatedBooking;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Shared setup. Any booking a test creates through createBooking() is deleted afterwards,
 * so the shared public API is left as clean as it was found.
 */
public abstract class BaseApiTest {

    protected BookingClient client;
    private final List<Integer> createdIds = new ArrayList<>();

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        client = new BookingClient(new AuthClient().validToken());
    }

    protected CreatedBooking createBooking(Booking booking) {
        CreatedBooking created = client.create(booking)
                .then().statusCode(200)
                .extract().as(CreatedBooking.class);
        createdIds.add(created.bookingid());
        return created;
    }

    @AfterClass(alwaysRun = true)
    public void cleanUp() {
        createdIds.forEach(id -> client.delete(id));
        createdIds.clear();
    }
}
