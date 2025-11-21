package com.project.ptittoanthu.majors.controller;

import com.project.ptittoanthu.common.base.builder.ResponseBuilder;
import com.project.ptittoanthu.common.base.dto.PageResponseDto;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.ResponseDto;
import com.project.ptittoanthu.common.base.enums.StatusCodeEnum;
import com.project.ptittoanthu.infra.language.LanguageService;
import com.project.ptittoanthu.majors.dto.CreateMajorRequest;
import com.project.ptittoanthu.majors.dto.MajorResponse;
import com.project.ptittoanthu.majors.dto.MajorResponseDetail;
import com.project.ptittoanthu.majors.dto.MajorSearchRequest;
import com.project.ptittoanthu.majors.dto.UpdateMajorRequest;
import com.project.ptittoanthu.majors.service.MajorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/majors")
@RequiredArgsConstructor
public class MajorController {

    private final MajorService majorService;
    private final LanguageService languageService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto<MajorResponseDetail>> createMajor(
            @Valid @RequestBody CreateMajorRequest request
    ) {
        MajorResponseDetail majorResponse = majorService.createMajor(request);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<MajorResponseDetail> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                majorResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<ResponseDto<MajorResponseDetail>> updateMajor(
            @Valid @RequestBody UpdateMajorRequest request
    ) {
        MajorResponseDetail majorResponse = majorService.updateMajor(request);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<MajorResponseDetail> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                majorResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<List<MajorResponse>>> pageMajor(
            @Valid @ParameterObject MajorSearchRequest searchRequest
    ) {
        PageResult<List<MajorResponse>> majorResponse = majorService.getMajors(searchRequest);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        PageResponseDto<List<MajorResponse>> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                majorResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<MajorResponseDetail>> getMajor(
            @PathVariable Integer id
    ) {
        MajorResponseDetail majorResponse = majorService.getMajor(id);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<MajorResponseDetail> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                majorResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> delete(
            @PathVariable Integer id
    ) {
        majorService.deleteMajor(id);

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
