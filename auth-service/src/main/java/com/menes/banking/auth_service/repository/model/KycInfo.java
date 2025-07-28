package com.menes.banking.auth_service.repository.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class KycInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String nationalId;
    private String address;
    private String phoneNumber;
    private String dateOfBirth;
    private String gender;
    private String nationality;

    @Enumerated(EnumType.STRING)
    private KycStatus status;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Profile user;
}