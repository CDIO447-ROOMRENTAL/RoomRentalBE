package com.example.room_rental.contract.repository;

import com.example.room_rental.contract.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract,String> {
    List<Contract> findByRenter(String id);
}
