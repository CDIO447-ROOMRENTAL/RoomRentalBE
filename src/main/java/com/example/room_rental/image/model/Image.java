package com.example.room_rental.image.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(generator = "prefixed-uuid")
    @GenericGenerator(
            name = "prefixed-uuid",
            strategy = "com.example.room_rental.utils.customid.PrefixedUuidGenerator",
            parameters = @Parameter(name = "prefix", value = "image-"))
    private String id;

    @Column(columnDefinition = "text")
    private String name;

    @Column(columnDefinition = "text")
    private String url;
}
