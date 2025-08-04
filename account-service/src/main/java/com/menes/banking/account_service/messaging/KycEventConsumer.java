package com.menes.banking.account_service.messaging;

import com.menes.banking.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component("profile-event-channel")
@RequiredArgsConstructor
@Slf4j
public class KycEventConsumer extends KafkaBaseConsumer {

    private final AccountService accountService;

    @KafkaListener(topics = "kyc.events", groupId = "account-service-group")
    @Override
    protected void handle(String payload) {

    }
}
