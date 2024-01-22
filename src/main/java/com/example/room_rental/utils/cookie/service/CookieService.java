package com.example.room_rental.utils.cookie.service;

import com.example.room_rental.utils.cookie.model.CookieRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieService {
    ResponseEntity<?> getCookie(HttpServletRequest request, String name);

    ResponseEntity<?> setCookie(HttpServletResponse response, CookieRequest cookieRequest);

    ResponseEntity<?> clearCookie(HttpServletResponse response, String name);
}
