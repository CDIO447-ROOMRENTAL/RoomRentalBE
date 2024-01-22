package com.example.room_rental.auth.dto.request;

import lombok.Data;

@Data
public class SignUpForm {
    private String name;
    private String username;
    private String email;
    private String password;
}
