package com.project.ptittoanthu.question.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateQuestionRequest {
    @Schema(name = "id", example = "1")
    @NotNull
    private Integer id;
    @Schema(name = "content", example = "Câu hỏi ví dụ?")
    private String content;
}
