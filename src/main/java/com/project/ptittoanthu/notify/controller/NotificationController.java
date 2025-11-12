package com.project.ptittoanthu.notify.controller;

import com.project.ptittoanthu.common.base.builder.ResponseBuilder;
import com.project.ptittoanthu.common.base.dto.PageResponseDto;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.PaginationRequest;
import com.project.ptittoanthu.common.base.dto.ResponseDto;
import com.project.ptittoanthu.common.base.enums.StatusCodeEnum;
import com.project.ptittoanthu.infra.language.LanguageService;
import com.project.ptittoanthu.notify.dto.NotificationResponse;
import com.project.ptittoanthu.notify.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final LanguageService languageService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<PageResponseDto<List<NotificationResponse>>> getNotifications(
            @Valid @ParameterObject PaginationRequest request
    ) {
        PageResult<List<NotificationResponse>> facultyResponse = notificationService.getNotifications(request);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        PageResponseDto<List<NotificationResponse>> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                facultyResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/read_all")
    public ResponseEntity<ResponseDto<Void>> readAll(
    ) {
        notificationService.readAll();

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<Void> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message)
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> delete(
            @PathVariable Integer id
    ) {
        notificationService.delete(id);

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
