package com.example.room_rental.user.model;

import org.hibernate.annotations.Parameter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set; // Correct import for Set
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import com.example.room_rental.auth.model.Role;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(generator = "prefixed-uuid")
    @GenericGenerator(
            name = "prefixed-uuid",
            strategy = "com.example.room_rental.utils.customid.PrefixedUuidGenerator",
            parameters = @Parameter(name = "prefix", value = "user-"))
    private String id;


    private String name;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(columnDefinition = "text")
    private String avatar;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}

