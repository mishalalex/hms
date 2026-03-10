package com.mishal.project.HMS.repository;

import com.mishal.project.HMS.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
