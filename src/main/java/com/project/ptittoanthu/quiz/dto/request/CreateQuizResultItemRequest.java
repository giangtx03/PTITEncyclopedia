package com.project.ptittoanthu.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateQuizResultItemRequest {
    @Schema(name = "questionId", example = "1")
    private Integer questionId;
    @Schema(name = "selectedId", example = "1")
    private Integer selectedId;
}
