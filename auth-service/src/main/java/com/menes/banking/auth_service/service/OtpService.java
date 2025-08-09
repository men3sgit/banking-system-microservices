package com.menes.banking.auth_service.service;

import com.menes.banking.auth_service.service.model.OtpResult;

public interface OtpService {

    OtpResult generateOtp(String subject);

    boolean validate(String subject, String otp);
}
