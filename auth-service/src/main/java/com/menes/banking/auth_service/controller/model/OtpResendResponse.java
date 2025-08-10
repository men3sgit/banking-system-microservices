package com.menes.banking.auth_service.controller.model;

import java.time.Instant;

public record OtpResendResponse(String otpId, Instant expiresAt, String message) {}