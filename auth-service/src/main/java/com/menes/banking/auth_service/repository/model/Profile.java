package com.menes.banking.auth_service.repository.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private boolean isVerified = false;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private KycInfo kycInfo;

}