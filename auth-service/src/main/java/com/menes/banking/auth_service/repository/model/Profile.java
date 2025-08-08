package com.menes.banking.auth_service.repository.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profiles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Profile extends EntityAuditor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @Column(nullable = false)
    private boolean isVerified = false;

    private String type;

    private String status;

}