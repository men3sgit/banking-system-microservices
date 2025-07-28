package com.menes.banking.auth_service.messaging.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event<D, C> {

    private String id;

    private D details;

    private C context;

    private String rootId;

    @NotNull
    @Positive
    private LocalDateTime time;

    @NotBlank
    private String source;

    public enum AckQueueType {
        KAFKA, RABBITMQ
    }
}
