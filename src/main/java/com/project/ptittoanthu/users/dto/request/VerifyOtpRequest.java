package com.project.ptittoanthu.users.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerifyOtpRequest {
    @Schema(name = "email", example = "user@gmail.com")
    @NotBlank(message = "valid.email.notBlank")
    private String email;
    @Size(min = 6, max = 6, message = "valid.otp.size")
    @Schema(name = "otp", example = "123456")
    private String otp;
}
