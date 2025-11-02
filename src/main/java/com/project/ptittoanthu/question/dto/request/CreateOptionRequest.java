package com.project.ptittoanthu.question.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateOptionRequest {
    @Schema(description = "Nhập đáp án cho sự lựa chọn")
    @NotBlank
    private String value;

    @Schema(description = "Nhập lựa chọn đúng hay sai")
    @NotNull
    private boolean correct;
}
