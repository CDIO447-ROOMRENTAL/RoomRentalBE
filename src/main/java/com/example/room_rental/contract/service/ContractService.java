package com.example.room_rental.contract.service;

import com.example.room_rental.contract.dto.request.ContractCreate;
import org.springframework.http.ResponseEntity;

public interface ContractService {
    ResponseEntity<?> createContract(ContractCreate contractCreate);

    ResponseEntity<?> getContract();
}
