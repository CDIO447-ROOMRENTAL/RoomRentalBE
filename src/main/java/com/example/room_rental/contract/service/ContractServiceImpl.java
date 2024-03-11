package com.example.room_rental.contract.service;

import com.example.room_rental.accommodation.model.Room;
import com.example.room_rental.accommodation.repository.RoomRepository;
import com.example.room_rental.auth.service.jwts.userdetail.UserPrinciple;
import com.example.room_rental.contract.dto.request.ContractCreate;
import com.example.room_rental.contract.model.Contract;
import com.example.room_rental.contract.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public ResponseEntity<?> createContract(ContractCreate contractCreate) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
            String userId = principal.getId();

            Optional<Room> roomOptional = roomRepository.findById(contractCreate.getRoomId());
            if (roomOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found with ID: " + contractCreate.getRoomId());
            }
            Contract contract = new Contract();
            contract.setFullname(contractCreate.getFullname());
            contract.setPhone(contractCreate.getPhone());
            contract.setEmail(contractCreate.getEmail());
            contract.setAddress(contractCreate.getAddress());
            contract.setIdCard(contractCreate.getIdCard());
            contract.setPaypal(contractCreate.getOrder());
            contract.setStartDate(LocalDateTime.now());
            contract.setEndDate(LocalDateTime.now().plus(1, ChronoUnit.MONTHS));

            contract.setRoom(roomOptional.get());
            contract.setRenter(userId);
            return ResponseEntity.status(HttpStatus.OK).body(contractRepository.save(contract));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the contract.");
        }
    }

    @Override
    public ResponseEntity<?> getContract() {
       try {
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           UserPrinciple principal = (UserPrinciple) authentication.getPrincipal();
           String userId = principal.getId();
           List<Contract> contracts= contractRepository.findByRenter(userId);
           return new ResponseEntity<>(contracts,HttpStatus.OK);
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the contract.");
       }
    }
}
