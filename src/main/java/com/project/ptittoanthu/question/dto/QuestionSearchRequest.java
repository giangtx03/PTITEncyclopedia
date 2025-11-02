package com.project.ptittoanthu.question.dto;

import com.project.ptittoanthu.common.base.dto.PaginationRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSearchRequest extends PaginationRequest {
    private Integer quizId;
}
