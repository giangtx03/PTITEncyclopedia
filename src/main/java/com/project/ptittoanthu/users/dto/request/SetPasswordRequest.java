package com.project.ptittoanthu.users.dto.request;

import com.project.ptittoanthu.common.base.annotation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SetPasswordRequest {
    @Schema(name = "password", example = "Abc123@")
    @NotBlank(message = "valid.password.notBlank")
    @ValidPassword(message = "valid.password.principle")
    private String password;
    @Schema(name = "token", example = "123anc")
    @NotBlank
    private String token;
}
