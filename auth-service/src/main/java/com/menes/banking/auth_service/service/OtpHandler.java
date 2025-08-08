package com.menes.banking.auth_service.service;

import com.menes.banking.auth_service.service.model.OtpContext;

public interface OtpHandler {

    String generate(String profileId, OtpContext ctx);

    boolean validate(String profileId, String otp, OtpContext ctx);

    String type();

}