package com.dilshana.api.tests;

import static org.assertj.core.api.Assertions.assertThat;

import com.dilshana.api.data.BookingFactory;
import com.dilshana.api.model.CreatedBooking;
import java.util.List;
import java.util.Map;
import org.testng.annotations.Test;

public class BookingSearchTest extends BaseApiTest {

    @Test(groups = "regression")
    public void searchByFirstNameFindsTheBooking() {
        String name = BookingFactory.uniqueName();
        CreatedBooking created = createBooking(BookingFactory.withName(name));

        List<Integer> ids = client.list(Map.of("firstname", name))
                .then().statusCode(200).extract().path("bookingid");

        assertThat(ids).containsExactly(created.bookingid());
    }

    @Test(groups = "regression")
    public void searchForAnUnknownNameReturnsNothing() {
        List<Integer> ids = client.list(Map.of("firstname", "NoSuchGuest" + BookingFactory.uniqueName()))
                .then().statusCode(200).extract().path("bookingid");

        assertThat(ids).isEmpty();
    }
}
