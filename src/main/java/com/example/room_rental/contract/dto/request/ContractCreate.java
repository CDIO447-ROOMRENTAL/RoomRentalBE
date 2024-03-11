package com.example.room_rental.contract.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContractCreate {
    private String fullname;
    private String phone;
    private String email;
    private String address;
    private String idCard;
    private String order;
    private LocalDateTime endDate;
    private String roomId;
}
