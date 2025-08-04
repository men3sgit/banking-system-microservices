package com.menes.banking.account_service.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AccountRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(
            regexp = "^(\\+\\d{1,3}[- ]?)?\\d{9,15}$",
            message = "Phone number should be valid (e.g., +84901234567 or 0901234567)"
    )
    private String phone;
}
