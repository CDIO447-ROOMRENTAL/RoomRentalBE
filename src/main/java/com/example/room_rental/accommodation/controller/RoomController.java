package com.example.room_rental.accommodation.controller;

import com.example.room_rental.accommodation.dto.request.RoomRequest;
import com.example.room_rental.accommodation.service.RoomService;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "/*")
@RequestMapping("accommodation/room")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @PostMapping("create")
    public ResponseEntity<?> createRoom(@RequestBody RoomRequest roomRequest){
        return roomService.createRoom(roomRequest);
    }
    @GetMapping("get")
    public ResponseEntity<?> getRooms(@PageableDefault(10) Pageable pageable,@RequestParam(required = false) String search, @RequestParam(required = true) String accommodationId){
        return roomService.listRoom(pageable,search,accommodationId);
    }
    @GetMapping("getById")
    public ResponseEntity<?> getRoomById(@RequestParam(required = true) String accommodationId,@RequestParam(required = true) String roomId){
        return roomService.getRoomById(accommodationId,roomId);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateRoom(@RequestParam(required = true) String roomId,@RequestBody RoomRequest roomRequest){
        return roomService.updateRoom(roomId,roomRequest);
    }

}
