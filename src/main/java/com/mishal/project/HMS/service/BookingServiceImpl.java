package com.mishal.project.HMS.service;

import com.mishal.project.HMS.dto.BookingDto;
import com.mishal.project.HMS.dto.BookingRequestDto;
import com.mishal.project.HMS.dto.GuestDto;
import com.mishal.project.HMS.entity.*;
import com.mishal.project.HMS.entity.enums.BookingStatus;
import com.mishal.project.HMS.exception.InventoryAlreadyBooked;
import com.mishal.project.HMS.exception.ResourceNotFoundException;
import com.mishal.project.HMS.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private final GuestRepository guestRepository;
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
            //if(inventory.getReservedCount()!=0) throw new InventoryAlreadyBooked("The inventory you are trying to reserve is already reserved!");
            inventory.setReservedCount(inventory.getBookedCount() + bookingRequestDto.getRoomsCount());
        }

        inventoryRepository.saveAll(inventoryList);

        // TODO - calculate dynamic amount to implement dynamic pricing
        Booking booking = Booking.builder()
                .status(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequestDto.getCheckInDate())
                .checkOutDate(bookingRequestDto.getCheckOutDate())
                .user(setDefaultUser())
                .roomsCount(bookingRequestDto.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {
        log.info("Attempting to add guests to the booking {}",
                bookingId);

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if(hasBookingExpired(booking)) throw new IllegalStateException("Booking has expired for booking id: " + bookingId);

        if(booking.getStatus()!= BookingStatus.RESERVED) throw new IllegalStateException("Cannot add guest as booking state not RESERVED, for booking id: " + bookingId);

        for(GuestDto guestDto: guestDtoList) {
            Guest guest = modelMapper.map(guestDto, Guest.class);
            guest.setUser(setDefaultUser());
            guestRepository.save(guest);
            booking.getGuests().add(guest);
        }
        booking.setStatus(BookingStatus.GUESTS_ADDED);
        bookingRepository.save(booking);

        return modelMapper.map(booking, BookingDto.class);
    }

    public boolean hasBookingExpired(Booking booking) {
        return booking.getCreated_at().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User setDefaultUser() {
        User user = new User();
        user.setId(1L);
        return user;
    }
}
