package com.mishal.project.HMS.dto;

import com.mishal.project.HMS.entity.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BookingStatus status;
    private Set<GuestDto> guests;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
