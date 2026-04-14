package com.mishal.project.HMS.service;

import com.mishal.project.HMS.dto.BookingDto;
import com.mishal.project.HMS.dto.BookingRequestDto;
import com.mishal.project.HMS.entity.*;
import com.mishal.project.HMS.entity.enums.BookingStatus;
import com.mishal.project.HMS.exception.ResourceNotFoundException;
import com.mishal.project.HMS.repository.BookingRepository;
import com.mishal.project.HMS.repository.HotelRepository;
import com.mishal.project.HMS.repository.InventoryRepository;
import com.mishal.project.HMS.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BookingDto initiateBooking(BookingRequestDto bookingRequestDto) {
        log.info("Attempting to initialise booking {} rooms in hotel with id {} room with id {}, between dates {} and {}",
                bookingRequestDto.getRoomsCount(),
                bookingRequestDto.getHotelId(),
                bookingRequestDto.getRoomId(),
                bookingRequestDto.getCheckInDate(),
                bookingRequestDto.getCheckOutDate());
        Hotel hotel = hotelRepository
                .findById(bookingRequestDto.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + bookingRequestDto.getHotelId()));

        Room room = roomRepository
                .findById(bookingRequestDto.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + bookingRequestDto.getRoomId()));

        List<Inventory> inventoryList = inventoryRepository
                .findAndLockInventory(
                        bookingRequestDto.getRoomId(),
                        bookingRequestDto.getCheckInDate(),
                        bookingRequestDto.getCheckOutDate(),
                        bookingRequestDto.getRoomsCount()
                );
        long daysCount = ChronoUnit.DAYS.between(bookingRequestDto.getCheckInDate(),bookingRequestDto.getCheckOutDate()) + 1;

        if(inventoryList.size() != daysCount) throw new IllegalStateException("Room is not available anymore!");

        // Reserve the room / update the booked count of inventories
        for(Inventory inventory: inventoryList) {
            inventory.setBookedCount(inventory.getBookedCount() + bookingRequestDto.getRoomsCount());
        }

        inventoryRepository.saveAll(inventoryList);

        // Create the booking with a dummy user
        User user = new User();
        user.setId(1L);

        // TODO - calculate dynamic amount to implement dynamic pricign
        Booking booking = Booking.builder()
                .status(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequestDto.getCheckInDate())
                .checkOutDate(bookingRequestDto.getCheckOutDate())
                .user(user)
                .roomsCount(bookingRequestDto.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }
}
