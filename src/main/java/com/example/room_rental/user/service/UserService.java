package com.example.room_rental.user.service;

import org.springframework.http.ResponseEntity;

import com.example.room_rental.user.dto.request.ProfileRequest;

public interface UserService {
    ResponseEntity<?> getById(String id);

    ResponseEntity<?> getProfile();

    ResponseEntity<?> updateProfile(ProfileRequest profileRequest);

    ResponseEntity<?> uploadImage(String imageUrl);
}