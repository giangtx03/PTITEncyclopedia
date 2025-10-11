package com.project.ptittoanthu.authentication.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Parameter(example = "user@gmail.com")
    @NotBlank(message = "email.not.blank")
    private String email;


    @Parameter(example = "user123")
    @NotBlank(message = "password.not.blank")
    private String password;
}
