package com.example.room_rental.auth.service.auth;

import com.example.room_rental.auth.dto.request.SignInForm;
import com.example.room_rental.auth.dto.request.SignUpForm;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

public interface AuthService {
    ResponseEntity<?> signUp(@Valid SignUpForm signUpForm, HttpServletResponse response);

    ResponseEntity<?> signIn(@Valid SignInForm signInForm, HttpServletResponse response);

    ResponseEntity<?> verify(String otp, HttpServletRequest request, HttpServletResponse response);

}
