package com.mishal.project.HMS.controller;

import com.mishal.project.HMS.dto.BookingDto;
import com.mishal.project.HMS.dto.BookingRequestDto;
import com.mishal.project.HMS.dto.GuestDto;
import com.mishal.project.HMS.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDto> initiateBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        return ResponseEntity.ok(bookingService.initiateBooking(bookingRequestDto));
    }

    @PostMapping("/{bookingId}/add-guests")
    public ResponseEntity<BookingDto> addGuests(
            @PathVariable Long bookingId,
            @RequestBody List<GuestDto> guestDtoList
            ){
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestDtoList));
    }
}
