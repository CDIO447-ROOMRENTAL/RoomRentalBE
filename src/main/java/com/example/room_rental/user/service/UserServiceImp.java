package com.example.room_rental.user.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.example.room_rental.auth.service.jwts.userdetail.UserPrinciple;
import com.example.room_rental.user.dto.reponse.UserResponse;
import com.example.room_rental.user.dto.request.ProfileRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.room_rental.user.model.User;
import com.example.room_rental.user.repository.UserRepository;
import org.springframework.security.core.Authentication;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> getById(String id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                UserResponse user = new UserResponse();
                BeanUtils.copyProperties(userOptional.get(), user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
            String userId = principal.getId();
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                UserResponse user = new UserResponse();
                BeanUtils.copyProperties(userOptional.get(), user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateProfile(ProfileRequest profileRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
            String userId = principal.getId();
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user= userOptional.get();
                user.setName(profileRequest.getName());
                user.setGender(profileRequest.getGender());
                user.setDob(profileRequest.getDob());
                user.setAddress(profileRequest.getAddress());
                user.setPhone(profileRequest.getPhone());
                userRepository.save(user);
                UserResponse userResponse = new UserResponse();
                BeanUtils.copyProperties(userOptional.get(), userResponse);
                return new ResponseEntity<>(userResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> uploadImage(String imageUrl) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
            String userId = principal.getId();
            Optional<User> userOptional = userRepository.findById(userId);
            System.out.println(userId);
            if (userOptional.isPresent()) {
                User user= userOptional.get();
                user.setAvatar(imageUrl);
                userRepository.save(user);
                UserResponse userResponse = new UserResponse();
                BeanUtils.copyProperties(userOptional.get(), userResponse);
                return new ResponseEntity<>(userResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}