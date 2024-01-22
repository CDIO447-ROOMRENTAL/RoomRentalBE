package com.example.room_rental.auth.dto.request;

import lombok.Data;

@Data
public class SignInForm {
    private String username;
    private String password;
}
