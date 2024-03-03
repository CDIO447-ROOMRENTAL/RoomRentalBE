package com.example.room_rental.accommodation.dto.request;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Null;

import org.springframework.data.geo.Point;

import com.example.room_rental.accommodation.model.Category;
import com.example.room_rental.image.model.Image;

import lombok.Data;

@Data
public class CreateAccommodationRequest {
    private String title;
    private String description;
    private Point location;
    private String address;
    private Set<Image> images = new HashSet<>();
    private Set<Category> categories = new HashSet<>();
}
