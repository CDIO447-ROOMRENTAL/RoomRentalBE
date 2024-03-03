package com.example.room_rental.accommodation.repository;

import com.example.room_rental.accommodation.model.Room;
import com.example.room_rental.user.model.User;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.room_rental.accommodation.model.Accommodation;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, String> {
    @Query(value = "SELECT * FROM accommodation " +
            "LEFT JOIN user ON (:userId IS NOT NULL OR accommodation.user_id = user.id) " +
            "WHERE (:userId IS NULL OR accommodation.user_id = :userId) " +
            "AND (accommodation.id LIKE CONCAT(:search, '%') OR accommodation.title LIKE CONCAT('%', :search, '%') OR accommodation.address LIKE CONCAT('%', :search, '%') OR accommodation.created_at LIKE CONCAT('%', :search, '%'))",
            countQuery = "SELECT count(*) FROM accommodation "
                    +
                    "WHERE (:userId IS NULL OR accommodation.user_id = :userId) " +
                    "AND (accommodation.id LIKE CONCAT(:search, '%') OR accommodation.title LIKE CONCAT('%', :search, '%') OR accommodation.address LIKE CONCAT('%', :search, '%') OR accommodation.created_at LIKE CONCAT('%', :search, '%'))",
            nativeQuery = true)
    Page<Accommodation> getAccommodations(
            Pageable pageable,
            @Param("userId") String userId,
            @Param("search") String search);

    @Query(value = "SELECT * FROM accommodation " +
            "WHERE (:userId IS NULL OR accommodation.user_id = :userId) " +
            "AND (accommodation.id = :id ) and accommodation.user_id = :userId", nativeQuery = true)
    Optional<Accommodation> getProductById(@Param("userId") String userId, @Param("id") String id);

    @Query(
            value = "SELECT * FROM accommodation " +
                    "LEFT JOIN accommodation_category ON accommodation_category.accommodation_id = accommodation.id " +
                    "WHERE " +
                    "(:category IS NULL OR accommodation_category.accommodation_category_id = :category) " + // Adjusted join condition
                    "AND (:search IS NULL OR " +
                    "(accommodation.id LIKE CONCAT(:search, '%') " +
                    "OR accommodation.title LIKE CONCAT('%', :search, '%') " +
                    "OR accommodation.created_at LIKE CONCAT('%', :search, '%'))) " +
                    "AND (:address IS NULL OR accommodation.address LIKE CONCAT('%', :address, '%')) " +
                    "ORDER BY " +
                    "CASE WHEN :sortDate = 'ASC' THEN accommodation.created_at END ASC, " +
                    "CASE WHEN :sortDate = 'DESC' THEN accommodation.created_at END DESC",
            countQuery = "SELECT count(*) FROM accommodation " +
                    "LEFT JOIN accommodation_category ON accommodation_category.accommodation_id = accommodation.id " +
                    "WHERE " +
                    "(:category IS NULL OR accommodation_category.accommodation_category_id = :category) " + // Adjusted join condition
                    "AND (:search IS NULL OR " +
                    "(accommodation.id LIKE CONCAT(:search, '%') " +
                    "OR accommodation.title LIKE CONCAT('%', :search, '%') " +
                    "OR accommodation.created_at LIKE CONCAT('%', :search, '%'))) " +
                    "AND (:address IS NULL OR accommodation.address LIKE CONCAT('%', :address, '%'))",
            nativeQuery = true)
    Page<Accommodation> getProductsPublic(Pageable pageable,
                                          @Param("search") String search,
                                          @Param("address") String address,
                                          @Param("sortDate") String sortDate,
                                          @Param("category") Integer category);



}