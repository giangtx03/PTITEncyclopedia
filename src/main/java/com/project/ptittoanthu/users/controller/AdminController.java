package com.project.ptittoanthu.users.controller;

import com.project.ptittoanthu.common.base.builder.ResponseBuilder;
import com.project.ptittoanthu.common.base.dto.PageResponseDto;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.ResponseDto;
import com.project.ptittoanthu.common.base.dto.SearchRequest;
import com.project.ptittoanthu.common.base.enums.StatusCodeEnum;
import com.project.ptittoanthu.infra.language.LanguageService;
import com.project.ptittoanthu.subjects.dto.response.SubjectResponse;
import com.project.ptittoanthu.users.dto.SearchUserRequest;
import com.project.ptittoanthu.users.dto.request.UpdateUserRequest;
import com.project.ptittoanthu.users.dto.response.UserResponseDetail;
import com.project.ptittoanthu.users.service.AdminManagementService;
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

import javax.management.relation.RoleInfoNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminManagementService adminManagementService;
    private final LanguageService languageService;

    @GetMapping("/users")
    public ResponseEntity<PageResponseDto<List<UserResponseDetail>>> getUsers(
            @Valid @ParameterObject SearchUserRequest searchRequest
    ) {
        PageResult<List<UserResponseDetail>> result = adminManagementService.getUsers(searchRequest);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        PageResponseDto<List<UserResponseDetail>> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                result
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseDto<UserResponseDetail>> getUser(
            @PathVariable Integer id
    ) {

        UserResponseDetail userResponse = adminManagementService.getUser(id);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<UserResponseDetail> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                userResponse
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @PutMapping(value = "/users")
    public ResponseEntity<ResponseDto<UserResponseDetail>> updateUser(
            @Valid @RequestBody UpdateUserRequest request) {

        UserResponseDetail userResponseDetail = adminManagementService.updateUser(request);

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

    @PostMapping("/users/{teacherId}/subject/{subjectId}")
    public ResponseEntity<ResponseDto<Void>> addTeacherToCourseSection(
            @PathVariable("teacherId") Integer teacherId,
            @PathVariable("subjectId") Integer subjectId
    ) throws RoleInfoNotFoundException {

        adminManagementService.addTeacherToCourseSection(teacherId, subjectId);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<Void> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message)
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @DeleteMapping("/users/{teacherId}/subject/{subjectId}")
    public ResponseEntity<ResponseDto<Void>> takeTeacherOutCourseSection(
            @PathVariable("teacherId") Integer teacherId,
            @PathVariable("subjectId") Integer subjectId
    ) {

        adminManagementService.takeTeacherOutCourseSection(teacherId, subjectId);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        ResponseDto<Void> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message)
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }

    @GetMapping("/users/{teacherId}/subjects")
    public ResponseEntity<PageResponseDto<List<SubjectResponse>>> getTheSubjectsOfTeacher(
            @PathVariable Integer teacherId,
            @Valid @ParameterObject SearchRequest searchRequest
    ) {
        PageResult<List<SubjectResponse>> result = adminManagementService.getTheSubjectsOfTeacher(teacherId, searchRequest);

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.REQUEST_SUCCESSFULLY;

        PageResponseDto<List<SubjectResponse>> responseDto = ResponseBuilder.okResponse(
                statusCodeEnum.code,
                languageService.getMessage(statusCodeEnum.message),
                result
        );
        return ResponseEntity
                .status(statusCodeEnum.httpStatusCode)
                .body(responseDto);
    }
}
