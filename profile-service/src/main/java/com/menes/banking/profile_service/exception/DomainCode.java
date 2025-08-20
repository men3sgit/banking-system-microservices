package com.menes.banking.profile_service.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;


@Getter
public enum DomainCode {

    // Common Code
    GENERIC_ERROR("000", "Internal Server Error"),
    MISSING_REQUIRED_FIELD("001", "Missing require field"),
    EMAIL_EXISTS("009", "Email already exists"),
    PROFILE_ALREADY_EXISTS("0006", "Profile already exists"),
    PROFILE_NOT_FOUND("0007", "Profile not found"),
    EVENT_MISSING_FIELD("0010", "Event missing field"),
    EVENT_UNSUPPORTED_TYPE("0011", "Event unsupported type");


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