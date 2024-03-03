package com.example.room_rental.accommodation.repository;

import com.example.room_rental.accommodation.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room,String> {
        @Query(value = "SELECT * FROM room r " +
                "JOIN accommodation a ON r.accommodation_id = a.id " +
                "WHERE (:search IS NULL OR r.number_room = :search OR r.floor = :search OR r.price = :search OR r.description LIKE %:search%) " +
                "AND (:accommodationId IS NULL OR r.accommodation_id = :accommodationId) " +
                "AND (:userId IS NULL OR a.user_id = :userId)",
                countQuery = "SELECT COUNT(*) FROM room r " +
                        "JOIN accommodation a ON r.accommodation_id = a.id " +
                        "WHERE (:search IS NULL OR r.number_room = :search OR r.floor = :search OR r.price = :search OR r.description LIKE %:search%) " +
                        "AND (:accommodationId IS NULL OR r.accommodation_id = :accommodationId) " +
                        "AND (:userId IS NULL OR a.user_id = :userId)",
                nativeQuery = true)
        Page<Room> getRooms(@Param("pageable") Pageable pageable,
                            @Param("search") String search,
                            @Param("accommodationId") String accommodationId,
                            @Param("userId") String userId);

    @Query(value = "SELECT * FROM room r " +
            "JOIN accommodation a ON r.accommodation_id = a.id " +
            "WHERE (r.id = :roomId) " +
            "AND (:accommodationId IS NULL OR r.accommodation_id = :accommodationId) " +
            "AND (:userId IS NULL OR a.user_id = :userId)",
            nativeQuery = true)
    Room getRoomById(@Param("accommodationId") String accommodationId,
                     @Param("roomId") String roomId,
                     @Param("userId") String userId);
}
