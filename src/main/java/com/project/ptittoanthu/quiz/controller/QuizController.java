package com.project.ptittoanthu.quiz.controller;

import com.project.ptittoanthu.common.base.builder.ResponseBuilder;
import com.project.ptittoanthu.common.base.dto.PageResponseDto;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.ResponseDto;
import com.project.ptittoanthu.common.base.enums.StatusCodeEnum;
import com.project.ptittoanthu.infra.language.LanguageService;
import com.project.ptittoanthu.quiz.dto.QuizSearchRequest;
import com.project.ptittoanthu.quiz.dto.request.CreateQuizRequest;
import com.project.ptittoanthu.quiz.dto.request.UpdateQuizRequest;
import com.project.ptittoanthu.quiz.dto.response.QuizResponse;
import com.project.ptittoanthu.quiz.dto.response.QuizResponseDetail;
import com.project.ptittoanthu.quiz.service.QuizService;
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
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/quizzes")
public class QuizController {

    private final QuizService quizService;
    private final LanguageService languageService;

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping
    public ResponseEntity<ResponseDto<QuizResponseDetail>> createQuiz(
            @Valid @RequestBody CreateQuizRequest request
    ) {
        QuizResponseDetail responseDetail = quizService.createQuiz(request);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<QuizResponseDetail> responseDto = ResponseBuilder.okResponse(
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
    public ResponseEntity<ResponseDto<QuizResponseDetail>> updateQuiz (
            @Valid @RequestBody UpdateQuizRequest request
    ) {
        QuizResponseDetail responseDetail = quizService.updateQuiz(request);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<QuizResponseDetail> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                responseDetail
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<List<QuizResponse>>> getQuizzes(
            @Valid @ParameterObject QuizSearchRequest searchRequest
    ) {
        PageResult<List<QuizResponse>> result = quizService.getQuizzes(searchRequest);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        PageResponseDto<List<QuizResponse>> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                result
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
        quizService.delete(id);

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
