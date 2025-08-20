package com.menes.banking.kyc_service.exception;

import lombok.Getter;

@Getter
public enum DomainCode {

    // Common Code
    GENERIC_ERROR("000", "Internal Server Error"),
    MISSING_REQUIRED_FIELD("001", "Missing require field"),
    EMAIL_EXISTS("009", "Email already exists"),
    PROFILE_NOT_FOUND("0007", "Profile not found");


    public static final String SERVICE_IDENTIFIER = "M_M";

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