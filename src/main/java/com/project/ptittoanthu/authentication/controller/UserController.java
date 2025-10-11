package com.project.ptittoanthu.authentication.controller;

import com.project.ptittoanthu.authentication.dto.response.UserResponse;
import com.project.ptittoanthu.authentication.mapper.UserMapper;
import com.project.ptittoanthu.authentication.model.User;
import com.project.ptittoanthu.authentication.repository.UserRepository;
import com.project.ptittoanthu.common.builder.ResponseBuilder;
import com.project.ptittoanthu.common.dto.ResponseDto;
import com.project.ptittoanthu.common.enums.StatusCodeEnum;
import com.project.ptittoanthu.common.util.SecurityUtils;
import com.project.ptittoanthu.infra.language.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final LanguageService languageService;
    private final UserRepository userRepo;
    private final UserMapper userMapper;
    @PreAuthorize("isAuthenticated()")

    @GetMapping("/me")
    public ResponseEntity<ResponseDto<UserResponse>> getMe() {
        String email = SecurityUtils.getUserEmailFromSecurity();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy email trong security"));
        UserResponse userResponse = userMapper.toResponse(user);

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
}
