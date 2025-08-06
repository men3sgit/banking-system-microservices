package com.menes.banking.auth_service.service;


public interface OtpService {
    /**
     * Tạo OTP cho user
     * @param userId ID của user
     * @return OTP vừa được tạo
     */
    String generateOtp(String userId);

    /**
     * Xác thực OTP của user
     * @param userId ID của user
     * @param otp OTP cần xác thực
     * @return true nếu hợp lệ, false nếu không
     */
    boolean validateOtp(String userId, String otp);
}
