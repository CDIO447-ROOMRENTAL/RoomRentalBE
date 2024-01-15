package com.example.room_rental.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.room_rental.auth.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,String>{
    
}