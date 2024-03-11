package com.example.room_rental.contract.controller;

import com.example.room_rental.contract.dto.request.ContractCreate;
import com.example.room_rental.contract.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "/*")
@RequestMapping("accommodation/room/contract")
public class ContractController {
    @Autowired
    private ContractService contractService;
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody ContractCreate contractCreate){
        return contractService.createContract(contractCreate);
    }
    @PostMapping("get")
    public ResponseEntity<?> getContract(){
        return contractService.getContract();
    }
}
