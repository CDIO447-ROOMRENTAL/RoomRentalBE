package com.example.room_rental.auth.service.role;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.room_rental.auth.constain.ERole;
import com.example.room_rental.auth.model.Role;
import com.example.room_rental.auth.repository.RoleRepository;

@Component
public class RoleInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        if (roleRepository.count() == 0) {

            for (ERole roleName : ERole.values()) {
                String idRole = roleName.toString().replace("ROLE_", "");
                Role role = new Role();
                role.setId(idRole.toLowerCase());
                role.setRole(roleName);
                roleRepository.save(role);
            }
        }
    }
}