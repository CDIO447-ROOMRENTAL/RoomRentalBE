package com.example.room_rental.user.model;

import com.example.room_rental.company.model.Company;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import org.hibernate.annotations.Parameter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set; // Correct import for Set
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.CreationTimestamp;

import com.example.room_rental.accommodation.model.Accommodation;
import com.example.room_rental.auth.model.Role;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
        @Id
        @GeneratedValue(generator = "prefixed-uuid")
        @GenericGenerator(name = "prefixed-uuid", strategy = "com.example.room_rental.utils.customid.PrefixedUuidGenerator", parameters = @Parameter(name = "prefix", value = "user-"))
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

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        private Set<Accommodation> accommodations = new HashSet<>();

        @ManyToMany
        @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
        private Set<Role> roles = new HashSet<>();

        @ManyToMany
        @JoinTable(name = "user_company", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "company_id"))
        private Set<Company> companies = new HashSet<>();

}
