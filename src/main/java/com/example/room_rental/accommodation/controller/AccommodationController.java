package com.example.room_rental.accommodation.controller;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.room_rental.accommodation.dto.request.CreateAccommodationRequest;
import com.example.room_rental.accommodation.service.AccommodationService;
import com.example.room_rental.accommodation.service.CategoryService;

@RestController
@RequestMapping("accommodation")
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<?> createAccommodation(@RequestBody CreateAccommodationRequest accommodationRequest) {
        return accommodationService.createAccommodation(accommodationRequest);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProducts(@PageableDefault(10) Pageable pageable,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) String search
            ) {
        return accommodationService.getProducts(pageable,page,search);
    }
    @GetMapping("/getById")
    public ResponseEntity<?> getProductById(@RequestParam(required = true) String id) {
        return accommodationService.getProductById(id);
    }

    @GetMapping("public/get")
    public ResponseEntity<?> getProductsPublic(@PageableDefault(10) Pageable pageable,
                                         @RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) String search,
                                         @RequestParam(required = false) String address,
                                         @RequestParam(required = false) String sortDate,
                                         @RequestParam(required = false) Integer category
    ) {
        System.out.println(page+" "+search+" "+address+" "+sortDate+" "+category);
        return accommodationService.getProductsPublic(pageable,page,search,address,sortDate,category);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateAccommodation(@RequestParam(required = true) String id ,@RequestBody CreateAccommodationRequest accommodationRequest) {
        return accommodationService.updateAccommodation(id,accommodationRequest);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        return categoryService.getCategories();
    }

}
