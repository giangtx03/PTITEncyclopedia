package com.project.ptittoanthu.quiz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class QuizResultResponseDetail extends QuizResultResponse{
    private List<QuizResultItemResponse> quizResultItems;
}
