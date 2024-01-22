package com.example.room_rental.auth.controller;

import com.example.room_rental.auth.dto.request.SignInForm;
import com.example.room_rental.auth.dto.request.SignUpForm;
import com.example.room_rental.auth.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInForm signInForm) {
        return authService.signIn(signInForm);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpForm signUpForm, HttpServletResponse response) {
        return authService.signUp(signUpForm, response);
    }
    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam("otp") String otp, HttpServletRequest request,HttpServletResponse response) {
        return authService.verify(otp, request,response);
    }
}

