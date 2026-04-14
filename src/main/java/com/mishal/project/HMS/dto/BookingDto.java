package com.mishal.project.HMS.dto;

import com.mishal.project.HMS.entity.Guest;
import com.mishal.project.HMS.entity.Hotel;
import com.mishal.project.HMS.entity.Room;
import com.mishal.project.HMS.entity.User;
import com.mishal.project.HMS.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
    private Hotel hotel;
    private Room room;
    private User user;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BookingStatus status;
    private Set<GuestDto> guests;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
