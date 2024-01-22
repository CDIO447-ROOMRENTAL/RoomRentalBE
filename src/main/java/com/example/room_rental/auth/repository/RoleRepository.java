package com.example.room_rental.auth.repository;

import com.example.room_rental.auth.constain.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.room_rental.auth.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,String>{
    Optional<Role> findByRole(ERole eRole);
}