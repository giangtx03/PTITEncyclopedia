package com.project.ptittoanthu.quiz.controller;

import com.project.ptittoanthu.common.base.builder.ResponseBuilder;
import com.project.ptittoanthu.common.base.dto.PageResponseDto;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.ResponseDto;
import com.project.ptittoanthu.common.base.enums.StatusCodeEnum;
import com.project.ptittoanthu.infra.language.LanguageService;
import com.project.ptittoanthu.quiz.dto.QuizResultSearchRequest;
import com.project.ptittoanthu.quiz.dto.request.CreateQuizResultRequest;
import com.project.ptittoanthu.quiz.dto.response.QuizResultResponse;
import com.project.ptittoanthu.quiz.dto.response.QuizResultResponseDetail;
import com.project.ptittoanthu.quiz.service.QuizResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/quiz_results")
public class QuizResultController {

    private final QuizResultService quizResultService;
    private final LanguageService languageService;
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<ResponseDto<QuizResultResponse>> createQuizResult(
            @Valid @RequestBody CreateQuizResultRequest request
    ) {
        QuizResultResponse responseDetail = quizResultService.createQuizResult(request);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<QuizResultResponse> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                responseDetail
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<List<QuizResultResponse>>> getQuizResults(
            @Valid @ParameterObject QuizResultSearchRequest searchRequest
    ) {
        PageResult<List<QuizResultResponse>> questionResponse = quizResultService.getQuizResults(searchRequest);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        PageResponseDto<List<QuizResultResponse>> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                questionResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<QuizResultResponseDetail>> getQuizResult(
            @PathVariable Integer id
    ) {
        QuizResultResponseDetail questionResponse = quizResultService.getQuizResult(id);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<QuizResultResponseDetail> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                questionResponse
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
        quizResultService.delete(id);

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
