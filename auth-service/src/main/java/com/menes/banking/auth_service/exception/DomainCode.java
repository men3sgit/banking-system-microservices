package com.menes.banking.auth_service.exception;

import lombok.Getter;

@Getter
public enum DomainCode {

    // Common Code
    GENERIC_ERROR("0000", "Internal Server Error"),
    MISSING_REQUIRED_FIELD("0001", "Missing require field"),
    PROFILE_NOT_FOUND("0007", "Profile not found");

    public static final String SERVICE_IDENTIFIER = "0601";

    private String externalCode;

    private String message;

    DomainCode(String externalCode, String message) {
        this.externalCode = externalCode;
        this.message = message;
    }

    public String toUniversalCode() {
        return SERVICE_IDENTIFIER + externalCode;
    }

}