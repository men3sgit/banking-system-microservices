package com.menes.banking.profile_service.repository.model;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

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
    @JoinColumn(name = "profile_id")
    private Profile profile;
}