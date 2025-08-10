package com.menes.banking.auth_service.messaging.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.menes.banking.auth_service.AuthServiceApplication;
import com.menes.banking.auth_service.config.serialize.LocalDateTimeDeserializer;
import com.menes.banking.auth_service.config.serialize.LocalDateTimeSerializer;
import com.menes.banking.auth_service.validator.RegexConstant;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event<D> {

    @Pattern(regexp = RegexConstant.UUID)
    @NotNull
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @Valid
    @NotNull
    private D data;

    @NotNull
    private EventType eventType;

    private String rootId;

    private MessageType messageType;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @NotNull
    @Builder.Default
    @Positive
    private LocalDateTime time = LocalDateTime.now();

    @NotBlank
    @Builder.Default
    private String source = AuthServiceApplication.class.getPackage().getName();


    private AckQueueType  ackQueueType;

    public enum AckQueueType {
        KAFKA, RABBITMQ, SQS
    }

    public enum MessageType {
        MESSAGE_OUT, MESSAGE_IN;
    }

}
