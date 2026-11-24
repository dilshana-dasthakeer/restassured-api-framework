package com.dilshana.api.data;

import com.dilshana.api.model.Booking;
import com.dilshana.api.model.BookingDates;
import java.util.UUID;

/** Builds booking test data. Names are unique per call, so filtering by name never finds another test's data. */
public final class BookingFactory {

    private BookingFactory() {
    }

    public static Booking valid() {
        return new Booking(uniqueName(), "Dasthakeer", 250, true,
                new BookingDates("2026-12-01", "2026-12-05"), "Breakfast");
    }

    public static Booking withName(String firstname) {
        return new Booking(firstname, "Dasthakeer", 120, false,
                new BookingDates("2027-01-10", "2027-01-12"), "Late checkout");
    }

    public static String uniqueName() {
        return "QA" + UUID.randomUUID().toString().substring(0, 8);
    }
}
