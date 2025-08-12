package com.menes.banking.profile_service.service.model;

import com.menes.banking.profile_service.repository.model.Profile;
import com.menes.banking.profile_service.repository.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private String id;
    private String idNumber;
    private String type;
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String currentAccountNo;
    private String cellphone;
    private String occupation;
    private LocalDateTime dateOfBirth;
    private String kycStatus;
    private String identityProfileStatus;
    private String onBoardingChannel;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<Address> addresses;

    public static ProfileResponse from(Profile profile, List<Address> addresses) {
        return ProfileResponse.builder()
                .id(profile.getId())
                .idNumber(profile.getIdNumber())
                .type(profile.getType())
                .username(profile.getUsername())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .middleName(profile.getMiddleName())
                .email(profile.getEmail())
                .currentAccountNo(profile.getCurrentAccountNo())
                .cellphone(profile.getCellphone())
                .occupation(profile.getOccupation())
                .dateOfBirth(profile.getDateOfBirth())
                .identityProfileStatus(profile.getIdentityProfileStatus())
                .onBoardingChannel(profile.getOnBoardingChannel())
                .createdDate(profile.getCreatedDate())
                .updatedDate(profile.getUpdatedDate())
//                .addresses(addresses != null
//                        ? addresses.stream().map(AddressResponse::fromEntity).collect(Collectors.toList())
//                        : null)
                .build();
    }
}
