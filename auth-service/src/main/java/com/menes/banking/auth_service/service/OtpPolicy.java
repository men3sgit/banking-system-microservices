package com.menes.banking.auth_service.service;

import com.menes.banking.auth_service.service.model.DeliveryChannel;

import java.util.List;

public interface OtpPolicy {

    int length();

    java.time.Duration ttl();

    int maxAttempts();

    boolean allowReuse();

    List<DeliveryChannel> channels(); // SMS/EMAIL/PUSH/… (nếu cần)
}