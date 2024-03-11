package com.example.room_rental.accommodation.model;

import com.example.room_rental.contract.model.Contract;
import com.example.room_rental.image.model.Image;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(generator = "prefixed-uuid")
    @GenericGenerator(name = "prefixed-uuid", strategy = "com.example.room_rental.utils.customid.PrefixedUuidGenerator", parameters = @Parameter(name = "prefix", value = "room-"))
    private String id;
    private Integer numberRoom;
    private Integer floor;
    private BigDecimal price;
    @Column(columnDefinition = "text")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", referencedColumnName = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonBackReference
    private Accommodation accommodation;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "room_image", joinColumns = @JoinColumn(name = "room_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
    @JsonManagedReference
    private Set<Image> images = new HashSet<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Contract> contracts = new HashSet<>();
}
