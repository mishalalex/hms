package com.mishal.project.HMS.dto;

import com.mishal.project.HMS.entity.HotelContactInfo;
import lombok.Data;

import java.util.List;

@Data
public class HotelDto {
    private Long id;
    private String name;
    private String city;
    private HotelContactInfo contactInfo;
    private Boolean active;
    private List<String> photos;
    private List<String> amenities;

}
