package com.project.ptittoanthu.quiz.dto.response;

import com.project.ptittoanthu.question.dto.response.OptionResponseDetail;
import com.project.ptittoanthu.question.dto.response.QuestionResponseDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class QuizResultItemResponse {
    private Integer id;
    private QuestionResponseDetail question;
    private OptionResponseDetail selected;
}
