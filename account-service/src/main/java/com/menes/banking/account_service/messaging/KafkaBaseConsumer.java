package com.menes.banking.account_service.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import java.time.Duration;
import java.util.function.Consumer;

@Slf4j
public abstract class KafkaBaseConsumer implements Consumer<Message<String>> {

    private static final long NACK_TIMEOUT = 1000;

    protected abstract void handle(String payload);

    @Override
    public final void accept(Message<String> message) {
        try {
            String payload = message.getPayload();
            this.handle(payload);
            ack(message);
        } catch (Exception ex) {
            log.error("HP_ERROR while processing message at message offset={} and partition={} for consumer_group={} with ex={}",
                    message.getHeaders().get(KafkaHeaders.OFFSET),
                    message.getHeaders().get(KafkaHeaders.PARTITION),
                    message.getHeaders().get(KafkaHeaders.GROUP_ID),
                    ex.toString());
            nack(message);
        }
    }

    private void ack(Message<String> message) {
        Acknowledgment acknowledgment = message.getHeaders()
                .get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if (acknowledgment != null) {
            acknowledgment.acknowledge();
        }
    }

    private void nack(Message<String> message) {
        Acknowledgment acknowledgment = message.getHeaders()
                .get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if (acknowledgment != null) {
            acknowledgment.nack(Duration.ofSeconds(KafkaBaseConsumer.NACK_TIMEOUT));
        }
    }
}