package com.example.room_rental.user.service;

import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> getById(String id);
}