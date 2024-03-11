package com.example.room_rental.accommodation.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import com.example.room_rental.accommodation.model.Room;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.room_rental.accommodation.dto.request.CreateAccommodationRequest;
import com.example.room_rental.accommodation.model.Accommodation;
import com.example.room_rental.accommodation.repository.AccommodationRepository;
import com.example.room_rental.auth.constain.ERole;
import com.example.room_rental.auth.model.Role;
import com.example.room_rental.auth.service.jwts.userdetail.UserPrinciple;
import com.example.room_rental.image.model.Image;
import com.example.room_rental.image.repository.ImageRepository;
import com.example.room_rental.user.model.User;
import com.example.room_rental.user.repository.UserRepository;

@Service
public class AccommodationServiceImp implements AccommodationService {

    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public ResponseEntity<?> createAccommodation(CreateAccommodationRequest accommodationRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
            String userId = principal.getId();
            Optional<User> userOptional = userRepository.findById(userId);
            Accommodation accommodation = new Accommodation();
            accommodation.setUser(userOptional.get());
            BeanUtils.copyProperties(accommodationRequest, accommodation);

            // Lưu trữ hình ảnh và set lại cho accommodation
            Set<Image> images = new HashSet<>();
            for (Image image : accommodationRequest.getImages()) {
                // Lưu hình ảnh và thêm vào tập hợp
                Image savedImage = imageRepository.save(image);
                images.add(savedImage);
            }
            accommodation.setImages(images);
            // Lưu accommodation
            Accommodation savedAccommodation = accommodationRepository.save(accommodation);
            return new ResponseEntity<>(savedAccommodation, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log lỗi để dễ dàng theo dõi và gỡ lỗi
            return new ResponseEntity<>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getProducts(Pageable pageable, Integer page, String search) {
        try {
            // Get the authenticated user's authorities
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

            // Check if the user has the role "ROLE_ADMIN"
            if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                // Handle the action for users with the "ROLE_ADMIN" role
                return handleAdminAction(pageable, page, search);
            }
            // Check if the user has the role "ROLE_PM"
            else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_PM"))) {
                // Handle the action for users with the "ROLE_PM" role
                return handlePmAction(pageable, page, search);
            }
            // If the user doesn't have either role, return an error response
            else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
            }
        } catch (Exception e) {
            // If an exception occurs during processing, return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL_SERVER_ERROR");
        }
    }

    // Method to handle the action for users with the "ROLE_ADMIN" role
    private ResponseEntity<?> handleAdminAction(Pageable pageable, Integer page, String search) {
        try {
            // Implement the logic for handling the action for "ROLE_ADMIN" users
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
            String userId = principal.getId();
            pageable.withPage(page);
            Page<Accommodation> accommodations = accommodationRepository.getAccommodations(pageable, null, search);
            return new ResponseEntity<>(accommodations, HttpStatus.OK);
        } catch (Exception e) {
            // If an exception occurs during processing, return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL_SERVER_ERROR");
        }
    }

    // Method to handle the action for users with the "ROLE_PM" role
    private ResponseEntity<?> handlePmAction(Pageable pageable, Integer page, String search) {
        try {
            // Implement the logic for handling the action for "ROLE_PM" users
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
            pageable.withPage(page);
            String userId = principal.getId();
            Page<Accommodation> accommodations = accommodationRepository.getAccommodations(pageable, userId, search);
            return new ResponseEntity<>(accommodations, HttpStatus.OK);
        } catch (Exception e) {
            // If an exception occurs during processing, return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL_SERVER_ERROR");
        }
    }


    @Override
    public ResponseEntity<?> getProductById(String id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
            String userId = principal.getId();
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                throw new UsernameNotFoundException("User not found with username: ");
            }

            Set<Role> roles = userOptional.get().getRoles();
            boolean isPm = false;
            boolean isAdmin = false;

            for (Role role : roles) {
                if (role.getRole() == ERole.ROLE_PM) {
                    isPm = true;
                } else if (role.getRole() == ERole.ROLE_ADMIN) {
                    isAdmin = true;
                }
            }

            Optional<Accommodation> accommodationOptional = null;
            HttpStatus status = HttpStatus.OK;

            if (isPm && !isAdmin) {
                accommodationOptional = accommodationRepository.getProductById(userId, id);
            } else if (isAdmin) {
                accommodationOptional = accommodationRepository.getProductById(null, id);
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            if (accommodationOptional.isPresent()) {
                return new ResponseEntity<>(accommodationOptional.get(), status);
            } else {
                return new ResponseEntity<>("INTERNAL_SERVER_ERROR", status);
            }
        } catch (Exception e) {
            // TODO: xử lý ngoại lệ
            return new ResponseEntity<>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateAccommodation(String id, CreateAccommodationRequest accommodationRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
            String userId = principal.getId();

            // Kiểm tra xem người dùng có tồn tại không
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                throw new UsernameNotFoundException("User not found with id: " + userId);
            }

            // Kiểm tra quyền hạn của người dùng
            Set<Role> roles = userOptional.get().getRoles();
            boolean isPm = roles.stream().anyMatch(role -> role.getRole() == ERole.ROLE_PM);
            boolean isAdmin = roles.stream().anyMatch(role -> role.getRole() == ERole.ROLE_ADMIN);

            Optional<Accommodation> accommodationOptional = null;
            HttpStatus status = HttpStatus.OK;

            // Lấy thông tin về chỗ ở dựa trên quyền hạn của người dùng
            if (isPm && !isAdmin) {
                accommodationOptional = accommodationRepository.getProductById(userId, id);
            } else if (isAdmin) {
                accommodationOptional = accommodationRepository.getProductById(null, id);
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            // Kiểm tra xem chỗ ở có tồn tại không
            if (accommodationOptional.isPresent()) {
                Accommodation accommodation = accommodationOptional.get();

                // Cập nhật thông tin của chỗ ở
                accommodation.setTitle(accommodationRequest.getTitle());
                accommodation.setAddress(accommodationRequest.getAddress());
                accommodation.setCategories(accommodationRequest.getCategories());
                accommodation.setDescription(accommodationRequest.getDescription());

                // Lưu trữ các hình ảnh mới vào cơ sở dữ liệu (nếu có)
                if (accommodationRequest.getImages() != null && !accommodationRequest.getImages().isEmpty()) {
                    Set<Image> newImages = new HashSet<>();
                    for (Image imageRequest : accommodationRequest.getImages()) {
                        Image image = new Image();
                        image.setUrl(imageRequest.getUrl());
                        newImages.add(imageRepository.save(image));
                    }
                    accommodation.setImages(newImages);
                }

                // Lưu thông tin cập nhật vào cơ sở dữ liệu
                return new ResponseEntity<>(accommodationRepository.save(accommodation), status);
            } else {
                return new ResponseEntity<>("Accommodation not found", status);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ
            return new ResponseEntity<>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<?> getProductsPublic(Pageable pageable, Integer page, String search, String address, String sortDate, Integer category) {
        try{
            if (page != null && page >= 0) {
                pageable = pageable.withPage(page);
            }
            Page<Accommodation> accommodations = accommodationRepository.getProductsPublic(pageable,search,address,sortDate,category);
            return new ResponseEntity<>(accommodations, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getPublicAccommodationById(String accommodationId) {
        try {
            Optional<Accommodation> optionalAccommodation = accommodationRepository.findById(accommodationId);
            return new ResponseEntity<>(optionalAccommodation.get(),HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get price min max: " + e.getMessage());
        }
    }

}
