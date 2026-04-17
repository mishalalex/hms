package com.mishal.project.HMS.service;

import com.mishal.project.HMS.dto.HotelDto;
import com.mishal.project.HMS.dto.HotelInfoDto;

import java.util.List;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);
    void activateHotel(Long id);
    HotelDto getHotelById(Long id);
    HotelDto updateHotel(Long id, HotelDto hotelDto);
    Boolean deleteHotelById(Long id);
    List<HotelDto> getAllHotels();
    HotelInfoDto getHotelDetailsById(Long id);
}
