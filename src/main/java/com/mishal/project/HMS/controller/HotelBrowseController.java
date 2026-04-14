package com.mishal.project.HMS.controller;

import com.mishal.project.HMS.dto.HotelDto;
import com.mishal.project.HMS.dto.HotelInfoDto;
import com.mishal.project.HMS.dto.HotelSearchRequestDto;
import com.mishal.project.HMS.service.HotelService;
import com.mishal.project.HMS.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @PostMapping("/search")
    public ResponseEntity<Page<HotelDto>> searchHotels(@RequestBody HotelSearchRequestDto hotelSearchRequestDto) {

        Page<HotelDto> page = inventoryService.searchHotels(hotelSearchRequestDto);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{hotelId}/details")
    public ResponseEntity<HotelInfoDto> getHotelDetails(@PathVariable long hotelId) {
        return new ResponseEntity<>(hotelService.getHotelDetailsById(hotelId), HttpStatus.OK);
    }
}
