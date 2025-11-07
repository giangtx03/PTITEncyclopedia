package com.project.ptittoanthu.quiz.dto.response;

import com.project.ptittoanthu.common.base.dto.BaseResponse;
import com.project.ptittoanthu.users.dto.response.UserResponse;
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
public class QuizResultResponse extends BaseResponse {
    private Integer id;
    private QuizResponse quiz;
    private float score;
    private UserResponse user;
}
