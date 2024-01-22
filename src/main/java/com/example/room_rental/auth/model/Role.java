package com.example.room_rental.auth.model;

import javax.persistence.*;

import com.example.room_rental.auth.constain.ERole;

import lombok.Data;

@Data
@Entity
public class Role {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private ERole role;
}