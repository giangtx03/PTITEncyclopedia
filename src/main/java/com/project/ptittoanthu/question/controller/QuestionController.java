package com.project.ptittoanthu.question.controller;

import com.project.ptittoanthu.common.base.builder.ResponseBuilder;
import com.project.ptittoanthu.common.base.dto.PageResponseDto;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.ResponseDto;
import com.project.ptittoanthu.common.base.enums.StatusCodeEnum;
import com.project.ptittoanthu.infra.language.LanguageService;
import com.project.ptittoanthu.question.dto.QuestionSearchRequest;
import com.project.ptittoanthu.question.dto.request.CreateOptionRequest;
import com.project.ptittoanthu.question.dto.request.CreateQuestionRequest;
import com.project.ptittoanthu.question.dto.request.CreateTipRequest;
import com.project.ptittoanthu.question.dto.request.UpdateQuestionRequest;
import com.project.ptittoanthu.question.dto.response.QuestionResponse;
import com.project.ptittoanthu.question.dto.response.QuestionResponseDetail;
import com.project.ptittoanthu.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final LanguageService languageService;

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping
    public ResponseEntity<ResponseDto<QuestionResponseDetail>> createQuestion(
            @Valid @RequestBody CreateQuestionRequest request
    ) {
        QuestionResponseDetail responseDetail = questionService.createQuestion(request);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<QuestionResponseDetail> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                responseDetail
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping(value = "/upload/{subjectId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<List<QuestionResponseDetail>>> createQuestionByFileUpload(
            @PathVariable("subjectId") Integer subjectId,
            @RequestParam("file") MultipartFile file
            ) throws BadRequestException {
        List<QuestionResponseDetail> responseDetail = questionService.createQuestionByExcelFile(subjectId, file);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<List<QuestionResponseDetail>> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                responseDetail
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping("{id}/tips")
    public ResponseEntity<ResponseDto<QuestionResponseDetail>> addTip(
            @PathVariable("id") Integer questionId,
            @Valid @RequestBody CreateTipRequest request
    ) {
        QuestionResponseDetail responseDetail = questionService.addTip(questionId, request);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<QuestionResponseDetail> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                responseDetail
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping("{id}/options")
    public ResponseEntity<ResponseDto<QuestionResponseDetail>> addOption(
            @PathVariable("id") Integer questionId,
            @Valid @RequestBody CreateOptionRequest request
    ) {
        QuestionResponseDetail responseDetail = questionService.addOption(questionId, request);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<QuestionResponseDetail> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                responseDetail
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PutMapping
    public ResponseEntity<ResponseDto<QuestionResponseDetail>> updateQuestion (
            @Valid @RequestBody UpdateQuestionRequest request
    ) {
        QuestionResponseDetail responseDetail = questionService.updateQuestion(request);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<QuestionResponseDetail> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                responseDetail
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<List<QuestionResponse>>> pageQuestion(
            @Valid @ParameterObject QuestionSearchRequest searchRequest
    ) {
        PageResult<List<QuestionResponse>> questionResponse = questionService.getQuestions(searchRequest);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        PageResponseDto<List<QuestionResponse>> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                questionResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> delete(
            @PathVariable Integer id
    ) {
        questionService.deleteQuestion(id);

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
