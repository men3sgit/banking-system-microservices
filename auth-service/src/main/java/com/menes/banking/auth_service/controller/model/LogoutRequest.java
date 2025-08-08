package com.menes.banking.auth_service.controller.model;

import jakarta.validation.constraints.NotBlank;

public class LogoutRequest {
    @NotBlank
    private String refreshToken;

}