package com.project.ptittoanthu.users.controller;

import com.project.ptittoanthu.users.dto.request.ChangePasswordRequest;
import com.project.ptittoanthu.users.dto.request.UpdateProfileRequest;
import com.project.ptittoanthu.users.dto.response.UserResponse;
import com.project.ptittoanthu.users.dto.response.UserResponseDetail;
import com.project.ptittoanthu.common.base.builder.ResponseBuilder;
import com.project.ptittoanthu.common.base.dto.ResponseDto;
import com.project.ptittoanthu.common.base.enums.StatusCodeEnum;
import com.project.ptittoanthu.infra.language.LanguageService;
import com.project.ptittoanthu.users.service.UserService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final LanguageService languageService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<ResponseDto<UserResponseDetail>> getMe() {

        UserResponseDetail userResponseDetail = userService.getMe();

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<UserResponseDetail> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                userResponseDetail
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<UserResponseDetail>> updateProfile(
            @Valid @ModelAttribute UpdateProfileRequest request) throws IOException {

        UserResponseDetail userResponseDetail = userService.updateProfile(request);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<UserResponseDetail> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                userResponseDetail
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping()
    public ResponseEntity<ResponseDto<Void>> deleteProfile() {

        userService.deleteProfile();

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<Void> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message)
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<UserResponse>> changePassword(
            @PathVariable Integer id
    ) {

        UserResponse userResponse = userService.getOtherProfile(id);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<UserResponse> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                userResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PutMapping("/change-password")
    public ResponseEntity<ResponseDto<Void>> getOtherProfile(
            @Valid @ParameterObject ChangePasswordRequest request
    ) {

        userService.changePassword(request);

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
