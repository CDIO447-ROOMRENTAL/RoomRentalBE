package com.example.room_rental.accommodation.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.room_rental.accommodation.constain.ECategory;
import com.example.room_rental.accommodation.model.Category;
import com.example.room_rental.accommodation.repository.CategoryRepository;

@Component
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void save() {
        try {
            List<Category> categories = new ArrayList<>(); // Initialize the list

            for (ECategory category : ECategory.values()) {
                Category newCategory = new Category();
                BeanUtils.copyProperties(category, newCategory);
                categories.add(newCategory); // Add newCategory to the list, not category
            }
            categoryRepository.saveAll(categories);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResponseEntity<?> getCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            return new ResponseEntity<>(categories,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
