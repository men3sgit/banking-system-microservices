package com.menes.banking.auth_service.service;

import com.menes.banking.auth_service.controller.model.OtpRequest;
import com.menes.banking.auth_service.service.model.OtpResult;

public interface OtpService {

    OtpResult generateOtp();

    boolean validate(OtpRequest request);

    OtpResult resend(String otpId);
}
