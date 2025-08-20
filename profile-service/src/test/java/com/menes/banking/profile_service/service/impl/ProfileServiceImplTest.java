package com.menes.banking.profile_service.service.impl;

import com.menes.banking.profile_service.messaging.model.ProfileEvent;
import com.menes.banking.profile_service.repository.ProfileRepository;
import com.menes.banking.profile_service.repository.model.Profile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private  ProfileServiceImpl profileService;


    @Test
    void shouldCreateNewProfile() {
        // given
        ProfileEvent profileEvent = createProfileEvent();

        profileService.createProfile(profileEvent);

        // then
        verify(profileRepository,times(1) ).findById(anyString());
        verify(profileRepository,times(1) ).save(any());


    }

    @Test
    void updateProfile() {
        // given
        ProfileEvent profileEvent = createProfileEvent();

        profileService.createProfile(profileEvent);

        // then
        verify(profileRepository,times(1)).findById(anyString());
        verify(profileRepository,times(1) ).save(any());
    }

    @Test
    void getProfile() {
        // given

    }


    private ProfileEvent createProfileEvent() {
        return ProfileEvent.builder()
                .profileId("pf1")
                .email("email1@vi.com")
                .type("type")
                .firstName("firstName1")
                .lastName("lastName1")
                .phoneNumber("0734567890")
                .status("status1")
                .build();
    }

    private Profile createProfile(){
        return Profile.builder()
                .email("email1@gmail.com")
                .firstName("firstName1")
                .lastName("lastName1")
                .cellphone("0734567890")
                .type("type")

                .build();
    }
}