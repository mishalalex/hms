package com.mishal.project.HMS.controller;

import com.mishal.project.HMS.dto.HotelDto;
import com.mishal.project.HMS.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto) {
        log.info("Processing the request to create new hotel with name: {}", hotelDto.getName());
        HotelDto hotelFromRepository = hotelService.createNewHotel(hotelDto);
        log.info("Successfully created new hotel with name: {}", hotelDto.getName());
        return new ResponseEntity<>(hotelFromRepository, HttpStatus.CREATED);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long hotelId) {
        log.info("Processing the request to retrieve a hotel with id: {}", hotelId);
        HotelDto hotelFromRepository = hotelService.getHotelById(hotelId);
        log.info("Successfully retrieved hotel with id: {}", hotelId);
        return new ResponseEntity<>(hotelFromRepository, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        log.info("Processing the request to retrieve all hotels");
        List<HotelDto> allHotelsFromRepository = hotelService.getAllHotels();
        log.info("Successfully retrieved all hotels");
        return new ResponseEntity<>(allHotelsFromRepository, HttpStatus.OK);
    }

    @PatchMapping("/{hotelId}")
    public ResponseEntity<Void> activateHotel(@PathVariable Long hotelId) {
        log.info("Processing the request to activate a hotel with id: {}", hotelId);
        hotelService.activateHotel(hotelId);
        log.info("Successfully activated hotel with id: {}", hotelId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable Long hotelId, @RequestBody HotelDto hotelDto) {
        log.info("Processing the request to update a hotel with id: {}", hotelId);
        HotelDto hotelFromRepository = hotelService.updateHotel(hotelId, hotelDto);
        log.info("Successfully updated the hotel with the id: {}", hotelId);
        return new ResponseEntity<>(hotelFromRepository, HttpStatus.OK);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long hotelId){
        log.info("Processing the request to delete a hotel with id: {}", hotelId);
        Boolean isDeleted = hotelService.deleteHotelById(hotelId);
        if(!isDeleted) throw new RuntimeException("Hotel could not be deleted with id");
        log.info("Successfully deleted hotel with id: {}", hotelId);
        return ResponseEntity.noContent().build();
    }
}
