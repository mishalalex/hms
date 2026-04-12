package com.mishal.project.HMS.service;

import com.mishal.project.HMS.dto.HotelDto;
import com.mishal.project.HMS.entity.Hotel;
import com.mishal.project.HMS.entity.Room;
import com.mishal.project.HMS.exception.HotelAlreadyActiveException;
import com.mishal.project.HMS.exception.ResourceNotFoundException;
import com.mishal.project.HMS.repository.HotelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class HotelServiceImpl implements HotelService {
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final HotelRepository hotelRepository;


    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Attempting to save hotel with name {} to database", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Hotel with name {} has been saved in database", hotel.getName());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void activateHotel(Long id) {
        log.info("Attempting to activate hotel with id: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found by id: " + id));
        if(hotel.getActive()) throw new HotelAlreadyActiveException("The hotel is already activated!");
        hotel.setActive(true);
        for(Room room: hotel.getRooms()){
            inventoryService.initializeRoomForOneYear(room);
        }
        hotelRepository.save(hotel);
        log.info("Hotel with id {} has been activated in the database", id);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Attempting to get hotel with id {} from database", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found by id: " + id));
        log.info("Hotel with id {} has been found", hotel.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotel(Long id, HotelDto hotelDto) {
        log.info("Attempting to fetch hotel details with id {} to database", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found by id: " + id));
        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        log.info("Hotel with id {} has been updated", hotel.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public Boolean deleteHotelById(Long id){
        log.info("Attempting to delete hotel with id {} from database", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found by id: " + id));
        for(Room room: hotel.getRooms()) {
            inventoryService.deleteFutureInventories(room);
        }
        hotelRepository.deleteById(id);

        log.info("Hotel with id {} has been deleted", id);
        return true;
    }

    @Override
    public List<HotelDto> getAllHotels() {
        List<HotelDto> allHotels = hotelRepository
                .findAll()
                .stream()
                .map((element) -> modelMapper.map(element, HotelDto.class))
                .collect(Collectors.toList());
        return allHotels;
    }
}
