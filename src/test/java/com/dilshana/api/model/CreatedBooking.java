package com.dilshana.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** The response to creating a booking: the new id plus the stored booking. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CreatedBooking(int bookingid, Booking booking) {
}
