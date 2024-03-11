package com.example.room_rental.accommodation.service;

import com.example.room_rental.accommodation.dto.request.RoomRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface RoomService {
    ResponseEntity<?> createRoom(RoomRequest roomRequest);

    ResponseEntity<?> listRoom(Pageable pageable, String search, String accommodationId);

    ResponseEntity<?> getRoomById(String accommodationId, String roomId);

    ResponseEntity<?> updateRoom(String roomId, RoomRequest roomRequest);

    ResponseEntity<?> getRoomsByAccommodationId(String accommodationId);

    ResponseEntity<?> getPriceMinMaxRoomsByAccommodationId(String accommodationId);
}
