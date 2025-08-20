package com.menes.banking.auth_service.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OtpResult {
    private String otpId;

    private String code;

    private int attempts;

    private Instant expiresAt;
}
