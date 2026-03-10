package com.mishal.project.HMS.service;

import com.mishal.project.HMS.dto.HotelDto;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);
    HotelDto getHotelById(Long id);
}
