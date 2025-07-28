package com.menes.banking.auth_service.messaging.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisteredEvent {

    private String email;

    private String username;

    private String verificationToken;
}