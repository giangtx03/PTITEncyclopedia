package com.project.ptittoanthu.favorite.controller;

import com.project.ptittoanthu.common.base.builder.ResponseBuilder;
import com.project.ptittoanthu.common.base.dto.PageResponseDto;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.ResponseDto;
import com.project.ptittoanthu.common.base.dto.SearchRequest;
import com.project.ptittoanthu.common.base.enums.StatusCodeEnum;
import com.project.ptittoanthu.favorite.model.FavoriteSubject;
import com.project.ptittoanthu.favorite.service.FavoriteSubjectService;
import com.project.ptittoanthu.infra.language.LanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/favorites")
public class FavoriteController {

    private final FavoriteSubjectService favoriteSubjectService;
    private final LanguageService languageService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/document/{subjectId}")
    public ResponseEntity<ResponseDto<Void>> toggleFavorite(
            @PathVariable("subjectId") Integer subjectId
    ) {
        favoriteSubjectService.toggleFavorite(subjectId);

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
    @GetMapping("/document/{subjectId}")
    public ResponseEntity<ResponseDto<Boolean>> isFavorite(
            @PathVariable("subjectId") Integer subjectId
    ) {
        Boolean response = favoriteSubjectService.isFavorite(subjectId);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<Boolean> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                response
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<PageResponseDto<List<FavoriteSubject>>> getMyFavorites(
            @Valid @ParameterObject SearchRequest searchRequest
    ) {
        PageResult<List<FavoriteSubject>> facultyResponse = favoriteSubjectService.getMyFavorites(searchRequest);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        PageResponseDto<List<FavoriteSubject>> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                facultyResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }
}
