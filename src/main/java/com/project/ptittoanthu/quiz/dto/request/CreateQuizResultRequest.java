package com.project.ptittoanthu.quiz.dto.request;

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
    @NotNull
    private Integer quizId;
    @NotEmpty
    List<CreateQuizResultItemRequest> quizResultItemRequests;
}
