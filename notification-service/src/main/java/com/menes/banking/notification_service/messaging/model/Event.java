package com.menes.banking.notification_service.messaging.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event<D> {

    private String id;

    private String rootId;

    private D data; // details

    private Type messageType;

    private EventType eventType;

    @NotNull
    @Positive
    private LocalDateTime timestamp;

    @JsonIgnore
    private String eventDestination;

    public enum Type {
        MESSAGE_OUT, MESSAGE_IN, MESSAGE_REPLY, MESSAGE_REPLY_WITH_ACK, ASYNC_TASK
    }

    public enum AckQueueType {
        SQS, MSK
    }

    public enum EventType {
        OTP_LOGIN,
        OTP_REGISTER,
        OTP_TRANSACTION,
        OTP_VERIFY_CONTACT,
        OTP_CHANGE_EMAIL,
        OTP_CHANGE_PHONE,
        OTP_RESET_PASSWORD,

        EMAIL_WELCOME,

        SMS_BALANCE_ALERT,
        SMS_PROMOTION,

        PUSH_TRANSACTION,
        PUSH_PROMOTION,
        PUSH_SECURITY_ALERT,


        SYSTEM_ALERT


    }
}
