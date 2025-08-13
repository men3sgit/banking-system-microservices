package com.menes.banking.profile_service.repository.model;

import com.menes.banking.profile_service.messaging.model.ProfileEvent;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "profiles")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends EntityAuditor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Auto-generate UUID
    private String id;

    @Column(name = "id_number", nullable = false, unique = true)
    private String idNumber;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "current_account_no", unique = true)
    private String currentAccountNo;

    @Column(nullable = false, unique = true)
    private String cellphone;

    @Column
    private String occupation;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_status", nullable = false)
    private ProfileStatus profileStatus;

    @Column(name = "identity_profile_status")
    private String identityProfileStatus;

    @Column(name = "onboarding_channel")
    private String onBoardingChannel;

    public enum ProfileStatus {
        ACTIVE, PENDING_APPROVAL, BLOCKED, CLOSED, INACTIVE, REJECTED
    }

    public static Profile from(ProfileEvent profileEvent) {
        return Profile.builder()
                .email(profileEvent.getEmail())
                .cellphone(profileEvent.getPhoneNumber())
                .build();
    }
}
