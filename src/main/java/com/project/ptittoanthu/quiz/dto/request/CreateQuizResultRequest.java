package com.project.ptittoanthu.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateQuizResultRequest {
    @Schema(name = "quizId", example = "1")
    @NotNull
    private Integer quizId;
    @Schema(name = "quizResultItemRequests")
    @NotEmpty
    List<CreateQuizResultItemRequest> quizResultItemRequests;
}
