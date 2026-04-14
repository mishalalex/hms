package com.mishal.project.HMS.controller;

import com.mishal.project.HMS.dto.BookingDto;
import com.mishal.project.HMS.dto.BookingRequestDto;
import com.mishal.project.HMS.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDto> initiateBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        return ResponseEntity.ok(bookingService.initiateBooking(bookingRequestDto));
    }
}
