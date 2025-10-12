package com.project.ptittoanthu.users.controller;

import com.project.ptittoanthu.users.dto.request.LoginRequest;
import com.project.ptittoanthu.users.dto.request.RegisterRequest;
import com.project.ptittoanthu.users.dto.request.VerifyOtpRequest;
import com.project.ptittoanthu.users.dto.request.SetPasswordRequest;
import com.project.ptittoanthu.users.dto.response.LoginResponse;
import com.project.ptittoanthu.users.dto.response.UserResponse;
import com.project.ptittoanthu.users.service.AuthService;
import com.project.ptittoanthu.common.base.builder.ResponseBuilder;
import com.project.ptittoanthu.common.base.dto.ResponseDto;
import com.project.ptittoanthu.common.base.enums.StatusCodeEnum;
import com.project.ptittoanthu.infra.language.LanguageService;
import com.project.ptittoanthu.infra.redis.LimitService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TooManyListenersException;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthService authService;
    private final LanguageService languageService;
    @Qualifier("redis")
    private final LimitService redisLimitService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponse>> login(
            @Valid @ParameterObject LoginRequest loginRequest
    ) throws TooManyListenersException {
        try {
            if (redisLimitService.isLoginBlocked(loginRequest.getEmail())) {
                throw new TooManyListenersException("5");
            }

            LoginResponse loginResponse = authService.login(loginRequest);
            redisLimitService.resetLoginAttempts(loginRequest.getEmail());

            StatusCodeEnum statusCodeEnum = StatusCodeEnum.LOGIN_SUCCESSFULLY;

            ResponseDto<LoginResponse> responseDto = ResponseBuilder.okResponse(
                    statusCodeEnum.code,
                    languageService.getMessage(statusCodeEnum.message),
                    loginResponse
            );
            return ResponseEntity
                    .status(statusCodeEnum.httpStatusCode)
                    .body(responseDto);
        } catch (Exception e) {
            redisLimitService.increaseLoginAttempts(loginRequest.getEmail());
            throw e;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<UserResponse>> register(
            @Valid @ParameterObject RegisterRequest registerRequest,
            HttpServletRequest httpRequest
    ) throws MessagingException, TooManyListenersException {
        String ipAddress = httpRequest.getRemoteAddr();

        if (redisLimitService.isRegisterBlocked(ipAddress)) {
            throw new TooManyListenersException("60");
        }

        UserResponse userResponse = authService.register(registerRequest);
        redisLimitService.increaseRegisterAttempts(ipAddress);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REGISTER_SUCCESSFULLY;

        ResponseDto<UserResponse> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                userResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PostMapping("/verify-activation")
    public ResponseEntity<ResponseDto<Void>> verifyActivation(
            @Valid @ParameterObject VerifyOtpRequest request
    ) {
        authService.activateAccount(request);
        StatusCodeEnum statusCodeEnum = StatusCodeEnum.ACTIVE_SUCCESSFULLY;

        ResponseDto<Void> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message)
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PostMapping("/resend-otp-activation")
    public ResponseEntity<ResponseDto<Void>> resendOtpActivation(
            @NotBlank(message = "valid.email.notBlank") @ParameterObject String email,
            HttpServletRequest request
    ) throws MessagingException, TooManyListenersException {
        String ipAddress = request.getRemoteAddr();
        if (redisLimitService.isRequestBlocked(ipAddress)) {
            throw new TooManyListenersException("10");
        }

        authService.resendEmailActive(email);
        redisLimitService.increaseRequestAttempts(ipAddress);
        StatusCodeEnum statusCodeEnum = StatusCodeEnum.SEND_OTP_SUCCESSFULLY;

        ResponseDto<Void> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message)
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDto<Void>> forgotPassword(
            @NotBlank(message = "valid.email.notBlank") @ParameterObject String email,
            HttpServletRequest request
    ) throws MessagingException, TooManyListenersException {
        String ipAddress = request.getRemoteAddr();
        if (redisLimitService.isRequestBlocked(ipAddress)) {
            throw new TooManyListenersException("10");
        }
        authService.forgotPassword(email);
        redisLimitService.increaseRequestAttempts(ipAddress);
        StatusCodeEnum statusCodeEnum = StatusCodeEnum.SEND_OTP_SUCCESSFULLY;

        ResponseDto<Void> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message)
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PostMapping("/verify-otp-forgot-password")
    public ResponseEntity<ResponseDto<String>> verifyOtpForgotPassword(
            @Valid @ParameterObject VerifyOtpRequest request
    ) {
        String token = authService.verifyOtpForgotPassword(request);
        StatusCodeEnum statusCodeEnum = StatusCodeEnum.OTP_VALID;

        ResponseDto<String> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                token
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PostMapping("/set-password")
    public ResponseEntity<ResponseDto<Void>> setPassword(
            @Valid @ParameterObject SetPasswordRequest request
    ) {
        authService.setPassword(request);
        StatusCodeEnum statusCodeEnum = StatusCodeEnum.CHANGE_PASSWORD_SUCCESSFULLY;

        ResponseDto<Void> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message)
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseDto<String>> refreshToken(
            @NotBlank @RequestHeader("RefreshToken") String refreshToken
    ) {
        String accessToken = authService.refreshToken(refreshToken);
        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<String> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                accessToken
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

//    @GetMapping("/social-login/{provider}")
//    public void getOAuth2LoginUrl(
//            @PathVariable String provider, HttpServletResponse response
//    ) throws IOException {
//        if (!ProviderEnum.isValid(provider)) {
//            throw new OAuth2AuthenticationException("Invalid provider: " + provider);
//        }
//
//        String redirectUri = "/oauth2/authorization/" + provider.toLowerCase();
//        response.sendRedirect(redirectUri);
//    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout(
            @NotBlank @RequestHeader("RefreshToken") String refreshToken
    ) {
        authService.logout(refreshToken);
        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<Void> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message)
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }
}
