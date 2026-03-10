package com.mishal.project.HMS.controller;

import com.mishal.project.HMS.dto.HotelDto;
import com.mishal.project.HMS.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {
    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto) {
        log.info("Attempting to create new hotel with name: {}", hotelDto.getName());
        HotelDto hotelFromRepository = hotelService.createNewHotel(hotelDto);
        log.info("Created new hotel with name: {}", hotelDto.getName());
        return new ResponseEntity<>(hotelFromRepository, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long hotelId) {
        log.info("Attempting to get hotel with id: {}", hotelId);
        HotelDto hotelFromRepository = hotelService.getHotelById(hotelId);
        return new ResponseEntity<>(hotelFromRepository, HttpStatus.OK);
    }
}
