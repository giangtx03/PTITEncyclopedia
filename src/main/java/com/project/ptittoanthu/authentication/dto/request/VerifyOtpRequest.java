package com.project.ptittoanthu.authentication.dto.request;

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
    @NotBlank(message = "valid.email.notBlank")
    private String email;
    @Size(min = 6, max = 6, message = "valid.otp.size")
    private String otp;
}
