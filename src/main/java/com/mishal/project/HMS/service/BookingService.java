package com.mishal.project.HMS.service;

import com.mishal.project.HMS.dto.BookingDto;
import com.mishal.project.HMS.dto.BookingRequestDto;
import com.mishal.project.HMS.dto.GuestDto;

import java.util.List;

public interface BookingService {
    BookingDto initiateBooking(BookingRequestDto bookingRequestDto);
    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
