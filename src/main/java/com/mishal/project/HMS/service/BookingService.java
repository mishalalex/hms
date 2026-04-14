package com.mishal.project.HMS.service;

import com.mishal.project.HMS.dto.BookingDto;
import com.mishal.project.HMS.dto.BookingRequestDto;
import com.mishal.project.HMS.entity.Booking;

public interface BookingService {
    BookingDto initiateBooking(BookingRequestDto bookingRequestDto);
}
