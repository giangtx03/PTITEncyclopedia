package com.project.ptittoanthu.common.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.ptittoanthu.authentication.exception.EmailExistsException;
import com.project.ptittoanthu.authentication.exception.UserIsAvtiveException;
import com.project.ptittoanthu.authentication.exception.UserNotFoundException;
import com.project.ptittoanthu.common.builder.ResponseBuilder;
import com.project.ptittoanthu.common.dto.ResponseDto;
import com.project.ptittoanthu.common.enums.StatusCodeEnum;
import com.project.ptittoanthu.infra.language.LanguageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final LanguageService languageService;

    /**
     * Ánh xạ các exception với mã lỗi tương ứng.
     */
    private static final Map<Class<? extends Exception>, StatusCodeEnum>
            EXCEPTION_STATUS_MAP = new HashMap<>();

    static {
        EXCEPTION_STATUS_MAP.put(EmailExistsException.class, StatusCodeEnum.EMAIL_IS_EXISTS);
        EXCEPTION_STATUS_MAP.put(UserNotFoundException.class, StatusCodeEnum.USER_NOT_FOUND);
        EXCEPTION_STATUS_MAP.put(LockedException.class, StatusCodeEnum.USER_LOCKED);
        EXCEPTION_STATUS_MAP.put(DisabledException.class, StatusCodeEnum.USER_UN_ACTIVE);
        EXCEPTION_STATUS_MAP.put(AuthenticationException.class, StatusCodeEnum.UNAUTHENTICATED);
        EXCEPTION_STATUS_MAP.put(BadCredentialsException.class, StatusCodeEnum.UNAUTHENTICATED);
        EXCEPTION_STATUS_MAP.put(UsernameNotFoundException.class, StatusCodeEnum.UNAUTHENTICATED);
        EXCEPTION_STATUS_MAP.put(AuthorizationDeniedException.class, StatusCodeEnum.ACCESS_DENIED);
        EXCEPTION_STATUS_MAP.put(JWTVerificationException.class, StatusCodeEnum.JWT_VERIFICATION_ERROR);
        EXCEPTION_STATUS_MAP.put(OtpInvalidException.class, StatusCodeEnum.OTP_INVALID);
        EXCEPTION_STATUS_MAP.put(UserIsAvtiveException.class, StatusCodeEnum.USER_IS_ACTIVE);
        EXCEPTION_STATUS_MAP.put(IOException.class, StatusCodeEnum.FILE_ERROR);
    }

    /**
     * Xử lý chung cho các exception có trong EXCEPTION_STATUS_MAP.
     */
    @ExceptionHandler({
        EmailExistsException.class, UserNotFoundException.class,
        LockedException.class, DisabledException.class,
        AuthenticationException.class, BadCredentialsException.class,
        AuthorizationDeniedException.class, JWTVerificationException.class,
        UsernameNotFoundException.class, OtpInvalidException.class,
        UserIsAvtiveException.class, IOException.class
    })
    public ResponseEntity<ResponseDto<Void>> handleMappedExceptions(Exception e) {
        log.error("Exception occurred: {}", e.getMessage());

        StatusCodeEnum statusCode = EXCEPTION_STATUS_MAP.getOrDefault(e.getClass(),
                StatusCodeEnum.SERVER_EXCEPTION);
        ResponseDto<Void> responseDto = ResponseBuilder.errorResponse(
                statusCode.code, languageService.getMessage(statusCode.message));

        return ResponseEntity.status(statusCode.httpStatusCode).body(responseDto);
    }

    /**
     * Xử lý lỗi giới hạn số lần request.
     */
    @ExceptionHandler(TooManyListenersException.class)
    public ResponseEntity<ResponseDto<Void>> handleTooManyRequests(TooManyListenersException e) {
        return buildErrorResponse(StatusCodeEnum.TOO_MANY_REQUEST, e.getMessage());
    }

    /**
     * Xử lý lỗi validation (MethodArgumentNotValidException).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDto<Map<String, List<String>>>> handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        Map<String, List<String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(error ->
                                getLocalizedMessage(error.getDefaultMessage()), Collectors.toList())
                ));

        return ResponseEntity.status(StatusCodeEnum.VALIDATION_ERROR.httpStatusCode)
                .body(ResponseBuilder.errorResponse(
                        StatusCodeEnum.VALIDATION_ERROR.code,
                        languageService.getMessage(StatusCodeEnum.VALIDATION_ERROR.message),
                        errors));
    }

    /**
     * Xử lý lỗi hệ thống chung.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseDto<Void>> handleGlobalException(Exception e) {
        log.error("Unhandled exception: {}", e.getMessage());
        return buildErrorResponse(StatusCodeEnum.SERVER_EXCEPTION, e.getMessage());
    }

    private ResponseEntity<ResponseDto<Void>> buildErrorResponse(
            StatusCodeEnum statusCode, String errorMessage
    ) {
        log.error("Error: {}", errorMessage);
        ResponseDto<Void> responseDto = ResponseBuilder.errorResponse(
                statusCode.code, languageService.getMessage(statusCode.message, errorMessage));
        return ResponseEntity.status(statusCode.httpStatusCode).body(responseDto);
    }

    private String getLocalizedMessage(String message) {
        try {
            String translatedMessage = languageService.getMessage(message);
            return StringUtils.hasText(translatedMessage) ? translatedMessage : message;
        } catch (Exception e) {
            return message;
        }
    }
}
