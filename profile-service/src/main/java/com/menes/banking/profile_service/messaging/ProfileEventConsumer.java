package com.menes.banking.profile_service.messaging;

import com.menes.banking.profile_service.config.props.ProfileEventProps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("profile-events-channel")
public class ProfileEventConsumer extends KafkaEventConsumer {

    private final MessageProducer messageProducer;

    private final ProfileEventProps  props;

    @Override
    protected void sendMessageToQueue(Object payload) {
        messageProducer.sendMessageToQueue(payload, props.getQueue());
    }
}
