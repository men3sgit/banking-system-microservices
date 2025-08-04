package com.menes.banking.account_service.repository.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "accounts")
public class Account {

    @Id
    private String id; // userId từ Auth Service (hoặc accountId duy nhất)

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private AccountStatus status; // PENDING_VERIFICATION, ACTIVE, LOCKED

    private String password;

    public enum AccountStatus {
        WAIT_FOR_APPROVAL,
        APPROVED,
        REJECTED,
        LOCKED
    }
}

