package com.example.room_rental.user.controller;

import com.example.room_rental.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "/*")
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("getById")
    public ResponseEntity<?> getById(@RequestParam ("id") String id ) {
        return userService.getById(id);
    }

}