package com.example.room_rental.company.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Company {
    @Id
    @GeneratedValue(generator = "prefixed-uuid")
    @GenericGenerator(name = "prefixed-uuid", strategy = "com.example.room_rental.utils.customid.PrefixedUuidGenerator", parameters = @Parameter(name = "prefix", value = "category-"))
    private String id;
    @Column(columnDefinition = "text")
    private String name;
    @Column(columnDefinition = "longtext")
    private String description;
    private String email;
    private String phone;
    @Column(columnDefinition = "text")
    private String website;
    @Column(columnDefinition = "text")
    private String address;
    @Column(columnDefinition = "text")
    private String logo;
}
