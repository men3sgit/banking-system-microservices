package com.menes.banking.auth_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class KycRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String nationalId;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String dateOfBirth;

    @NotBlank
    private String gender;

    @NotBlank
    private String nationality;
}