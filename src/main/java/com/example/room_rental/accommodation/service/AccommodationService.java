package com.example.room_rental.accommodation.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.example.room_rental.accommodation.dto.request.CreateAccommodationRequest;

public interface AccommodationService {

    ResponseEntity<?> createAccommodation(CreateAccommodationRequest accommodationRequest);

    ResponseEntity<?> getProducts(Pageable pageable,Integer page,String search);

    ResponseEntity<?> getProductById(String id);

    ResponseEntity<?> updateAccommodation(String id, CreateAccommodationRequest accommodationRequest);


    ResponseEntity<?> getProductsPublic(Pageable pageable, Integer page, String search, String address, String sortDate, Integer category);
}