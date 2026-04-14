package com.mishal.project.HMS.dto;

import com.mishal.project.HMS.entity.User;
import com.mishal.project.HMS.entity.enums.Gender;
import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private String name;
    private Gender gender;
    private Integer age;
    private User user;
}
