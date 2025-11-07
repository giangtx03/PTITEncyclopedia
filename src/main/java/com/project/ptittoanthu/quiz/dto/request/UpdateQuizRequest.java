package com.project.ptittoanthu.quiz.dto.request;

import com.project.ptittoanthu.quiz.model.QuizTime;
import com.project.ptittoanthu.quiz.model.QuizType;
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
    @NotNull
    private Integer id;
    @NotEmpty
    private String name;
    @NotNull
    private QuizType type;
    @NotNull
    private QuizTime time;
}
