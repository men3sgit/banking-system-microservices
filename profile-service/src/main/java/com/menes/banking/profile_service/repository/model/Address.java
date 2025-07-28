package com.menes.banking.profile_service.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "addresses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Sinh ID dạng UUID
    private String id;

    @Column(name = "additional_address")
    private String additionalAddress;

    @Column(name = "administrative_area_level1")
    private String administrativeAreaLevel1;

    @Column
    private String state;

    @Column
    private String city;

    @Column(name = "country_code", length = 5)
    private String countryCode;

    @Column(name = "zip_code", length = 10)
    private String zipCode;

    @Column(name = "mail_code", length = 20)
    private String mailCode;

    @Column(name = "address_str", length = 500)
    private String addressStr;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "address_type")
    private String addressType;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
