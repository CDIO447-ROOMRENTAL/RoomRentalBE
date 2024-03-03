package com.example.room_rental.user.controller;

import com.example.room_rental.user.dto.request.AvatarRequest;
import com.example.room_rental.user.dto.request.ProfileRequest;
import com.example.room_rental.user.service.UserService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "/*")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getById")
    public ResponseEntity<?> getById(@RequestParam("id") String id) {
        return userService.getById(id);
    }

    @GetMapping("/getProfile")
    public ResponseEntity<?> getProfile() {
        return userService.getProfile();
    }
    @PostMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody ProfileRequest profileRequest) {
        return userService.updateProfile(profileRequest);
    }
    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@Valid @RequestBody AvatarRequest avatarRequest) {
        return userService.uploadImage(avatarRequest.getAvatar());
    }

}