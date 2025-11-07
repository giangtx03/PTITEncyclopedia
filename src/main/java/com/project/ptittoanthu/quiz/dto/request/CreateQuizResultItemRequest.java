package com.project.ptittoanthu.quiz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateQuizResultItemRequest {
    private Integer questionId;
    private Integer selectedId;
}
