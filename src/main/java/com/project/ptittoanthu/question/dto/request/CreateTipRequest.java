package com.project.ptittoanthu.question.dto.request;

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
public class CreateTipRequest {
    @Schema(description = "Nhập gợi ý cho câu hỏi này")
    @NotBlank
    private String content;
}
