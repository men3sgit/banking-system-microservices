package com.menes.banking.profile_service.messaging.model;

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

    private MessageType messageType;

    private EventType eventType;

    @NotNull
    @Positive
    private LocalDateTime timestamp;

    @JsonIgnore
    private String eventDestination;

    public enum MessageType {
        MESSAGE_OUT, MESSAGE_IN, MESSAGE_REPLY, MESSAGE_REPLY_WITH_ACK, ASYNC_TASK
    }

    public enum AckQueueType {

        RABBIT, SQS, MSK

    }

    public enum EventType {

        PROFILE_CREATED,

        PROFILE_UPDATED
    }
}
