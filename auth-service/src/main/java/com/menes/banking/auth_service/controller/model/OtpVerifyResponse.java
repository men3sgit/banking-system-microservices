package com.menes.banking.auth_service.controller.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OtpVerifyResponse {
    private boolean success;

    private String message;
}
