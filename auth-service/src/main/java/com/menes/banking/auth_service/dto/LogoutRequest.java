package com.menes.banking.auth_service.dto;

import jakarta.validation.constraints.NotBlank;

public class LogoutRequest {
    @NotBlank
    private String refreshToken;

}