package com.mishal.project.HMS.repository;

import com.mishal.project.HMS.dto.HotelPriceDto;
import com.mishal.project.HMS.entity.Hotel;
import com.mishal.project.HMS.entity.HotelMinPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface HotelMinPriceRepository extends JpaRepository<HotelMinPrice, Long> {
    @Query(
            """
            SELECT new com.mishal.project.HMS.dto.HotelPriceDto(hmp.hotel, AVG(hmp.price))
            FROM HotelMinPrice hmp
            WHERE hmp.hotel.city= :city
                    AND hmp.date BETWEEN :startDate AND :endDate
                    AND hmp.hotel.active = true
            GROUP BY hmp.hotel
            """
    )
    Page<HotelPriceDto> findByHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );

    Optional<HotelMinPrice> findByHotelAndDate(Hotel hotel, LocalDate date);
}
