package com.menes.banking.auth_service.messaging.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpNotificationEvent {

    private String phoneNumber;

    private String otp;

    private List<String> channels;

    private LocalDateTime expiryTime;

}
