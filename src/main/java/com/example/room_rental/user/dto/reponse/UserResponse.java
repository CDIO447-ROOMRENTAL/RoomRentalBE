package com.example.room_rental.user.dto.reponse;

import com.example.room_rental.auth.model.Role;
import com.example.room_rental.user.constain.EGender;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserResponse {
    private String id;
    private String name;
    private EGender gender;
    private LocalDate dob;
    private String phone;
    private String address;

    private String username;
    private String email;

    private LocalDateTime createdAt;
    private String avatar;
    private Set<Role> roles = new HashSet<>();
}
