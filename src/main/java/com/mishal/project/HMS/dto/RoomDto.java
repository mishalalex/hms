package com.mishal.project.HMS.dto;

import com.mishal.project.HMS.entity.Hotel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RoomDto {
    private Long id;
    private String type;
    private BigDecimal basePrice;
    private Integer totalCount;
    private Integer capacity;
    private List<String> photos;
    private List<String> amenities;
}
