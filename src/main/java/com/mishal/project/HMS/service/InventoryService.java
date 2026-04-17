package com.mishal.project.HMS.service;

import com.mishal.project.HMS.dto.HotelDto;
import com.mishal.project.HMS.dto.HotelSearchRequestDto;
import com.mishal.project.HMS.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initializeRoomForOneYear(Room room);
    void deleteAllInventories(Room room);
    Page<HotelDto> searchHotels(HotelSearchRequestDto hotelSearchRequestDto);
}