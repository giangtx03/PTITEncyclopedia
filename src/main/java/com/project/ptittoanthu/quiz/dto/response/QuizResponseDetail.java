package com.project.ptittoanthu.quiz.dto.response;

import com.project.ptittoanthu.question.dto.response.QuestionResponse;
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
public class QuizResponseDetail extends QuizResponse{
    private List<QuestionResponse> questions;
}
