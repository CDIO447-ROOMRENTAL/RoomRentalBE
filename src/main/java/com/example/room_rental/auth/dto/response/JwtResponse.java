package com.example.room_rental.auth.dto.response;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class JwtResponse {
    private String accessToken;

    private String id;

    private String name;

    private String username;

    private String email;
    private String avatar;

    private Collection<? extends GrantedAuthority> roles;
    public JwtResponse(String jwt, String id, String name, String username, String email, String avatar,Collection<? extends GrantedAuthority> roles) {
        this.accessToken = jwt;
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.roles = roles;
    }

}