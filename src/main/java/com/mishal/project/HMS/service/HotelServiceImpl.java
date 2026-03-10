package com.mishal.project.HMS.service;

import com.mishal.project.HMS.dto.HotelDto;
import com.mishal.project.HMS.entity.Hotel;
import com.mishal.project.HMS.exception.ResourceNotFoundException;
import com.mishal.project.HMS.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Saving hotel with name {} to database", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Hotel with name {} has been saved in database", hotel.getName());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting hotel with id {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found by id: {}" + id));
        log.info("Hotel with id {} has been found", hotel.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }
}
