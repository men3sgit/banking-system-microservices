package com.menes.banking.auth_service.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String errorMessage, Object... args) {
        super(String.format(errorMessage, args));
    }
}