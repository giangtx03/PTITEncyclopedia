package com.project.ptittoanthu.quiz.dto;

import com.project.ptittoanthu.common.base.dto.SearchRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class QuizResultSearchRequest extends SearchRequest {
    private Integer subjectId;
    private float score;
}
