package com.example.room_rental.auth.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.example.room_rental.auth.constain.ERole;

import lombok.Data;

@Data
@Entity
public class Role {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private ERole role;
}