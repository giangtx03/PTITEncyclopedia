package com.project.ptittoanthu.users.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "email", example = "user@gmail.com")
    @NotBlank(message = "email.not.blank")
    private String email;


    @Schema(name = "password", example = "user123")
    @NotBlank(message = "password.not.blank")
    private String password;
}
