package com.menes.banking.auth_service.service;

import com.menes.banking.auth_service.dto.*;


public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(RefreshTokenRequest request);

    void logout(LogoutRequest request);

    void callProfileClient(String request);
}