package com.example.room_rental.contract.model;

import com.example.room_rental.accommodation.model.Room;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity

public class Contract {
    @Id
    @GeneratedValue(generator = "prefixed-uuid")
    @GenericGenerator(name = "prefixed-uuid", strategy = "com.example.room_rental.utils.customid.PrefixedUuidGenerator", parameters = @Parameter(name = "prefix", value = "contract-"))
    private String id;
    private String fullname;
    private String phone;
    private String email;
    private String address;
    private String idCard;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String renter;

    @Column(name = "paypal", columnDefinition = "TEXT")
    private String paypal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonBackReference
    private Room room;
}
