package com.menes.banking.notification_service.messaging.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OtpEvent {

    private String profileId;

    private String otpCode;

    private List<String> channels;

    private LocalDateTime expiryTime;

}
