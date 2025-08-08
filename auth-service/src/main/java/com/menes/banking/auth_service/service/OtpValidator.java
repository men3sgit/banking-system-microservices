package com.menes.banking.auth_service.service;

import com.menes.banking.auth_service.service.model.OtpContext;

public interface OtpValidator {

    boolean validate(String profileId, String otp, OtpContext ctx);

}