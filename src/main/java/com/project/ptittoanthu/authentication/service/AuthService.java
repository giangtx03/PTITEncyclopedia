package com.project.ptittoanthu.authentication.service;

import com.project.ptittoanthu.authentication.dto.request.LoginRequest;
import com.project.ptittoanthu.authentication.dto.request.RegisterRequest;
import com.project.ptittoanthu.authentication.dto.request.SetPasswordRequest;
import com.project.ptittoanthu.authentication.dto.request.VerifyOtpRequest;
import com.project.ptittoanthu.authentication.dto.response.LoginResponse;
import com.project.ptittoanthu.authentication.dto.response.UserResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public interface AuthService {
    void logout(@NotBlank String refreshToken);

    String refreshToken(@NotBlank String refreshToken);

    void setPassword(@Valid SetPasswordRequest request);

    String verifyOtpForgotPassword(@Valid VerifyOtpRequest request);

    void forgotPassword(@NotBlank(message = "valid.email.notBlank") String email) throws MessagingException;

    void resendEmailActive(@NotBlank(message = "valid.email.notBlank") String email) throws MessagingException;

    void activateAccount(@Valid VerifyOtpRequest request);

    UserResponse register(@Valid RegisterRequest registerRequest) throws MessagingException;

    LoginResponse login(@Valid LoginRequest loginRequest);
}
