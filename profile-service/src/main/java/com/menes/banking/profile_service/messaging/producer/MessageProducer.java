package com.menes.banking.profile_service.messaging.producer;

public interface MessageProducer {

    void sendMessageToQueue(Object payload, String destination);

}
