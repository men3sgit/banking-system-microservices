package com.menes.banking.profile_service.messaging;

public interface MessageProducer {

    void sendMessageToQueue(Object payload, String destination);

}
