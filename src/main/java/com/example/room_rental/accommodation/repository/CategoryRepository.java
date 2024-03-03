package com.example.room_rental.accommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.room_rental.accommodation.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String>{
    
}