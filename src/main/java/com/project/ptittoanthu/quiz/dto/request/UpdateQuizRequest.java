package com.project.ptittoanthu.quiz.dto.request;

import com.project.ptittoanthu.quiz.model.QuizTime;
import com.project.ptittoanthu.quiz.model.QuizType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateQuizRequest {
    @Schema(name = "id", example = "1")
    @NotNull
    private Integer id;
    @Schema(name = "title", example = "Final Exam")
    @NotEmpty
    private String title;
    @Schema(name = "type", example = "EXAM")
    @NotNull
    private QuizType type;
    @Schema(name = "time", example = "NINETY")
    @NotNull
    private QuizTime time;
}
