package com.example.room_rental.accommodation.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

@Entity
@Data
public class Category {
    @Id
    private Long id;
    private String name;
    @Column(columnDefinition = "text")
    private String image;
}
