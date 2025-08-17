package com.menes.banking.notification_service.exception;

import com.menes.banking.auth_service.exception.DomainCode;
import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    private final String errorCode;

    private final String errorMessage;

    private final DomainCode domainCode;

    public DomainException(DomainCode domainCode) {
        super(String.format(domainCode.getMessage(), (Object) null), null);
        this.errorCode = domainCode.toUniversalCode();
        this.errorMessage = domainCode.getMessage();
        this.domainCode = domainCode;
    }
}