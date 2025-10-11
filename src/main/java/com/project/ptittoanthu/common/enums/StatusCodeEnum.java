package com.project.ptittoanthu.common.enums;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum StatusCodeEnum {
    //  Common
    REQUEST_SUCCESSFULLY(1000, "common.request.successfully", HttpStatus.ACCEPTED),
    SERVER_EXCEPTION(1050, "common.server.error", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR(1051, "common.validation.error", HttpStatus.BAD_REQUEST),
    JWT_VERIFICATION_ERROR(1052, "common.jwt.verification.error", HttpStatus.FORBIDDEN),
    FILE_ERROR(1053, "common.file.error", HttpStatus.BAD_REQUEST),
    //    Auth 11xx
    LOGIN_SUCCESSFULLY(1100, "auth.login.successfully", HttpStatus.ACCEPTED),
    REGISTER_SUCCESSFULLY(1101, "auth.register.successfully", HttpStatus.CREATED),
    ACTIVE_SUCCESSFULLY(1102, "auth.active.successfully", HttpStatus.ACCEPTED),
    SEND_OTP_SUCCESSFULLY(1103, "auth.send.otp.successfully", HttpStatus.ACCEPTED),
    OTP_VALID(1104, "auth.otp.valid", HttpStatus.ACCEPTED),
    CHANGE_PASSWORD_SUCCESSFULLY(1105, "auth.change.password.successfully", HttpStatus.ACCEPTED),
    UNAUTHENTICATED(1150, "auth.authenticated.error", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(1151, "auth.access.denied", HttpStatus.FORBIDDEN),
    TOO_MANY_REQUEST(1152, "auth.too.many.request", HttpStatus.BAD_REQUEST),
    OTP_INVALID(1153, "auth.otp.invalid", HttpStatus.BAD_REQUEST),
    OAUTH2_AUTHENTICATION_ERROR(1154, "auth.oauth2.authenticated.error", HttpStatus.UNAUTHORIZED),
    //    User 12xx
    USER_LOCKED(1250, "user.locked.error", HttpStatus.BAD_REQUEST),
    USER_UN_ACTIVE(1251, "user.un_active.error", HttpStatus.BAD_REQUEST),
    USER_IS_ACTIVE(1252, "user.is_active.error", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1253, "user.not.found", HttpStatus.NOT_FOUND),
    EMAIL_IS_EXISTS(1254, "user.email.isExists", HttpStatus.BAD_REQUEST);
    public final int code;
    public final String message;
    public final HttpStatusCode httpStatusCode;

    StatusCodeEnum(int code, String message, HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.code = code;
    }
}
