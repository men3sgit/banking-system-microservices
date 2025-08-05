package com.menes.banking.profile_service.controller.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class KycRequest {
    private String idNumber;
    private LocalDate dob;
    private String address;
    private String imageUrl;
}