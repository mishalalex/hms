package com.mishal.project.HMS.controller;

import com.mishal.project.HMS.dto.HotelDto;
import com.mishal.project.HMS.dto.HotelSearchRequestDto;
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

    @PostMapping("/search")
    public ResponseEntity<Page<HotelDto>> searchHotels(@RequestBody HotelSearchRequestDto hotelSearchRequestDto) {

        Page<HotelDto> page = inventoryService.searchHotels(hotelSearchRequestDto);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
