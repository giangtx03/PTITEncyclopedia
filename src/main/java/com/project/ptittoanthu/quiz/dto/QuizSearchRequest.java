package com.project.ptittoanthu.quiz.dto;

import com.project.ptittoanthu.common.base.dto.SearchRequest;
import com.project.ptittoanthu.quiz.model.QuizTime;
import com.project.ptittoanthu.quiz.model.QuizType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class QuizSearchRequest extends SearchRequest {
    private Integer subjectId;
    private QuizType type;
    private QuizTime time;
}
