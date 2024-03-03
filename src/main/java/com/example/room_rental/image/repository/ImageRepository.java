package com.example.room_rental.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.room_rental.accommodation.model.Accommodation;
import com.example.room_rental.image.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image,String>{
    
}
