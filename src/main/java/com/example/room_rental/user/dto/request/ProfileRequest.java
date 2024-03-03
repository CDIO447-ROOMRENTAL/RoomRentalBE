package com.example.room_rental.user.dto.request;

import java.time.LocalDate;

import com.example.room_rental.user.constain.EGender;

import lombok.Data;

@Data
public class ProfileRequest {
    private String name;
    private EGender gender;
    private LocalDate dob;
    private String phone;
    private String address;
}
