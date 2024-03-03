package com.example.room_rental.accommodation.dto.request;

import com.example.room_rental.image.model.Image;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class RoomRequest {
    private String accommodationId;
    private Integer numberRoom;
    private Integer floor;
    private BigDecimal price;
    private String description;
    private Set<Image> images = new HashSet<>();
}
