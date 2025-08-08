package com.menes.banking.auth_service.messaging.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public abstract class KafkaEventConsumer implements Consumer<Message<String>> {

    protected abstract String destinationQueue();

    @Override
    public final void accept(Message<String> message) {
        try {
            var payload = message.getPayload();
//            sqsMessageProducer.sendToQueue(payload, destinationQueue());
            this.ack(message);
        } catch (RuntimeException ex) {
            this.nack(message);
            log.error("Exception while consuming event from Kafka - message {}",
                    message, ex);
        }
    }

    void nack(Message<String> message) {
        Optional.ofNullable(message.getHeaders()
                        .get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class))
                .ifPresent(acknowledgment -> acknowledgment.nack(Duration.ofSeconds(5L)));
    }

    void ack(Message<String> message) {
        Optional.ofNullable(message.getHeaders()
                        .get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class))
                .ifPresent(Acknowledgment::acknowledge);
    }
}