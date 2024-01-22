package com.example.room_rental.utils.cookie.controller;

import com.example.room_rental.utils.cookie.model.CookieRequest;
import com.example.room_rental.utils.cookie.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("cookie")
public class CookieController {
    @Autowired
    private CookieService cookieService;
    @GetMapping("/get")
    public ResponseEntity<?> getCookie(HttpServletRequest request, @RequestParam("name") String name){
        return cookieService.getCookie(request, name);
    }
    @PostMapping("/set")
    public ResponseEntity<?> setCookie(HttpServletResponse response, @RequestBody CookieRequest cookieRequest){
        return cookieService.setCookie(response, cookieRequest);
    }
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCookie(HttpServletResponse response,@RequestParam("name") String name) {
        return cookieService.clearCookie(response, name);
    }
}
