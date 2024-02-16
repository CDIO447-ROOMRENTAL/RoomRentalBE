package com.example.room_rental.accommodation.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.example.room_rental.image.model.Image;

import lombok.Data;

@Entity
@Data
public class Room {
    @Id
    private Long id;
    private Integer numberRoom;
    private Integer floor;
    private BigDecimal price;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(columnDefinition = "longtext")
    private String description;
    @ManyToMany
    @JoinTable(name = "room_image", joinColumns = @JoinColumn(name = "room_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> images = new HashSet<>();
}
