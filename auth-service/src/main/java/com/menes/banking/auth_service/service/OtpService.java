package com.menes.banking.auth_service.service;


import java.util.Map;

public interface OtpService {

    String generateOtp(String profileId, String type, Map<String,Object> attrs);

    boolean validateOtp(String profileId, String type, String otp);

}
