package com.menes.banking.auth_service.messaging.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpEvent {

    private String optId;

    private String code;

    private String destination;

    private String channel;

    private LocalDateTime expiresAt;
}
