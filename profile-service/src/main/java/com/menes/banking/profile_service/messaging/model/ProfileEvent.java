package com.menes.banking.profile_service.messaging.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProfileEvent {

    @Pattern(regexp = RegexConstant.UUID)
    @NotBlank
    private String profileId;

    private String phoneNumber;

    @Email
    private String email;

    @NotNull
    @Size(min = 1, max = 100)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 100)
    private String lastName;

    @NotNull
    private String type;

    @NotNull
    private String status;
}
