package com.example.room_rental.accommodation.service;

import com.example.room_rental.accommodation.dto.request.RoomRequest;
import com.example.room_rental.accommodation.model.Accommodation;
import com.example.room_rental.accommodation.model.Room;
import com.example.room_rental.accommodation.repository.AccommodationRepository;
import com.example.room_rental.accommodation.repository.RoomRepository;
import com.example.room_rental.auth.service.jwts.userdetail.UserPrinciple;
import com.example.room_rental.image.model.Image;
import com.example.room_rental.image.repository.ImageRepository;
import com.example.room_rental.user.model.User;
import com.example.room_rental.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> createRoom(RoomRequest roomRequest) {
        try {
            // Get the authenticated user's ID
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
            String userId = principal.getId();

            // Find the accommodation by ID
            Optional<Accommodation> optionalAccommodation = accommodationRepository.findById(roomRequest.getAccommodationId());
            if (!optionalAccommodation.isPresent()) {
                // If the accommodation does not exist, return 404 Not Found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Accommodation not found");
            }

            // Check if the authenticated user owns the accommodation
            Accommodation accommodation = optionalAccommodation.get();
            if (!accommodation.getUser().getId().equals(userId)) {
                // If the user does not own the accommodation, return 403 Forbidden
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
            }

            // Create a new room object and set its attributes
            Room room = new Room();
            room.setNumberRoom(roomRequest.getNumberRoom());
            room.setFloor(roomRequest.getFloor());
            room.setPrice(roomRequest.getPrice());
            room.setDescription(roomRequest.getDescription());

            // Convert ArrayList<Image> to Set<Image> and save images
            Set<Image> images = new HashSet<>();
            for (Image image : roomRequest.getImages()) {
                Image savedImage = imageRepository.save(image);
                images.add(savedImage);
            }
            room.setImages(images);

            // Set the accommodation for the room
            room.setAccommodation(accommodation);

            // Save the room
            Room savedRoom = roomRepository.save(room);

            // Return the saved room and HTTP status 201 Created
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
        } catch (Exception e) {
            // If an exception occurs, return HTTP status 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create room: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> listRoom(Pageable pageable, String search, String accommodationId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
            String userId = principal.getId();
            Page<Room> roomPage = roomRepository.getRooms(pageable, search, accommodationId,userId);
            return ResponseEntity.ok(roomPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to list rooms: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getRoomById(String accommodationId, String roomId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
            String userId = principal.getId();
            Room roomPage = roomRepository.getRoomById(accommodationId,roomId,userId);
            return ResponseEntity.ok(roomPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to list rooms: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateRoom(String roomId, RoomRequest roomRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
            String userId = principal.getId();
            Room roomPage = roomRepository.getRoomById(roomRequest.getAccommodationId(), roomId, userId);
            if (roomPage == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
            }
            Set<Image> images = roomPage.getImages();
            roomPage.getImages().clear();
            imageRepository.deleteAll(images);
            Set<Image> newImages = new HashSet<>();
            for (Image image : roomRequest.getImages()) {
                newImages.add(imageRepository.save(image));
            }
            roomPage.setImages(newImages);
            roomPage.setNumberRoom(roomRequest.getNumberRoom());
            roomPage.setFloor(roomRequest.getFloor());
            roomPage.setPrice(roomRequest.getPrice());
            roomPage.setDescription(roomRequest.getDescription());
            return ResponseEntity.ok(roomPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update room: " + e.getMessage());
        }
    }
}
