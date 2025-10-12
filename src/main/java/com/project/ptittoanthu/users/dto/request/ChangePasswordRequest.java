package com.project.ptittoanthu.users.dto.request;

import com.project.ptittoanthu.common.base.annotation.ValidPassword;
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
public class ChangePasswordRequest {
    @Parameter(example = "user123")
    @NotBlank(message = "password.not.blank")
    private String oldPassword;

    @NotBlank(message = "password.not.blank")
    @ValidPassword(message = "valid.password.principle")
    private String newPassword;
}
