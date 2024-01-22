package com.example.room_rental.utils.cookie.model;

import lombok.Data;

@Data
public class CookieRequest {
    private String name;
    private String value;
    private Integer expried;
}
