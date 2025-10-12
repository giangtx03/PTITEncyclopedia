package com.project.ptittoanthu.users.dto.request;

import com.project.ptittoanthu.common.base.annotation.ValidBirthDate;
import com.project.ptittoanthu.common.base.annotation.ValidEmail;
import com.project.ptittoanthu.common.base.annotation.ValidPassword;
import com.project.ptittoanthu.common.base.annotation.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "valid.email.notBlank")
    @ValidEmail(message = "valid.email.principle")
    private String email;

    @NotBlank(message = "valid.password.notBlank")
    @ValidPassword(message = "valid.password.principle")
    private String password;

    @NotBlank(message = "valid.username.notBlank")
    private String username;

    @NotBlank(message = "valid.phoneNumber.notBlank")
    @ValidPhoneNumber(message = "valid.phoneNumber.principle")
    private String phoneNumber;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "valid.birthDate.notNull")
    @ValidBirthDate(message = "valid.birthDate.principle")
    private LocalDate dob;
}
