package com.example.room_rental.accommodation.model;

import com.example.room_rental.image.model.Image;
import com.example.room_rental.user.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.geo.Point;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Accommodation {

        @Id
        @GeneratedValue(generator = "prefixed-uuid")
        @GenericGenerator(name = "prefixed-uuid", strategy = "com.example.room_rental.utils.customid.PrefixedUuidGenerator", parameters = @Parameter(name = "prefix", value = "product-"))
        private String id;
        @Column(columnDefinition = "text")
        private String title;
        // @Column(columnDefinition = "text")
        // private String name;
        @Column(columnDefinition = "longtext")
        private String description;
        private Point location;
        @Column(columnDefinition = "text")
        private String address;
        @CreationTimestamp
        private LocalDateTime createdAt;
        @ManyToMany(cascade = CascadeType.ALL)
        @JoinTable(name = "accommodation_image", joinColumns = @JoinColumn(name = "accommodation_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
        private Set<Image> images = new HashSet<>();

        @ManyToMany
        @JoinTable(name = "accommodation_category", joinColumns = @JoinColumn(name = "accommodation_id"), inverseJoinColumns = @JoinColumn(name = "accommodation_category_id"))
        private Set<Category> categories = new HashSet<>();

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        @JsonIdentityReference(alwaysAsId = true)
        @JsonBackReference
        private User user;

        @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
        @JsonIgnore
        private Set<Room> rooms = new HashSet<>();

}
