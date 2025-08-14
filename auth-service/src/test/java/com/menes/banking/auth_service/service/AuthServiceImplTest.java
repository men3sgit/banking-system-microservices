package com.menes.banking.auth_service.service;


import com.menes.banking.auth_service.config.properties.InternalTopicProperties;
import com.menes.banking.auth_service.controller.model.RegisterRequest;
import com.menes.banking.auth_service.exception.DomainCode;
import com.menes.banking.auth_service.exception.DomainException;
import com.menes.banking.auth_service.messaging.producer.KafkaProducer;
import com.menes.banking.auth_service.repository.model.Profile;
import com.menes.banking.auth_service.service.impl.AuthServiceImpl;
import com.menes.banking.auth_service.service.model.OtpResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    private static final String MOCK_ID = "MOCK_ID";

    @Mock
    private KafkaProducer kafkaProducer;

    @Mock
    private InternalTopicProperties internalTopicProperties;

    @Mock
    private OtpService otpService;

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        System.out.println("setUp before method");
    }


    @Test
    void shouldReturnWAITING_FOR_APPROVAL_whenRegisterNewProfileSuccessfully() {
        // given
        var registerRequest = createRequestRequest();
        var storedProfile = createProfile();
        var otpResult = createOtpResult();

        //when
        when(internalTopicProperties.getProfileTopicName()).thenReturn("profile-topic");
        when(internalTopicProperties.getNotificationTopicName()).thenReturn("notification-topic");
        when(profileService.existsEmail(any())).thenReturn(false);
        when(profileService.createProfile(any())).thenReturn(storedProfile);
        when(otpService.generateOtp()).thenReturn(otpResult);

        var result = authService.register(registerRequest);

        // then
        verify(profileService, times(1)).createProfile(any());
        verify(profileService, times(1)).existsEmail(any());
        verify(otpService, times(1)).generateOtp();
        verify(internalTopicProperties, times(1)).getProfileTopicName();
        verify(internalTopicProperties, times(1)).getNotificationTopicName();
        verify(kafkaProducer, times(2)).publishEvent(any(), anyString());

        assertThat(((Profile)result.getData()).getStatus(), equalTo("WAITING_FOR_APPROVAL"));
    }

    @Test
    void shouldThrowsDomainException_whenEmailAlreadyExists() {
        // given
        RegisterRequest registerRequest = createRequestRequest();

        // When + Then
        when(profileService.existsEmail(any())).thenReturn(true);
        DomainException ex = assertThrows(DomainException.class, () -> authService.register(registerRequest));
        assertEquals(DomainCode.EMAIL_EXISTS, ex.getDomainCode());

        // Verify tương tác: chỉ check tồn tại, không tạo profile, không gửi OTP/event
        verify(profileService, times(1)).existsEmail(any());
        verify(profileService, never()).createProfile(any());
        verifyNoInteractions(otpService, kafkaProducer);
    }

    private RegisterRequest createRequestRequest() {
        return RegisterRequest.builder()
                .username("username")
                .email("email@email.com")
                .firstName("firstName")
                .lastName("lastName")
                .phoneNumber("0323456789")
                .build();
    }

    private Profile createProfile() {
        return Profile.builder()
                .id(MOCK_ID)
                .firstName("firstName")
                .lastName("lastName")
                .phoneNumber("0323456789")
                .isVerified(false)
                .status("WAITING_FOR_APPROVAL")
                .build();
    }

    private OtpResult createOtpResult() {
        return OtpResult.builder()
                .otpId("otpId")
                .expiresAt(Instant.now())
                .code("123456")
                .attempts(0)
                .build();
    }

}
