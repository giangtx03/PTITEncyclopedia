package com.project.ptittoanthu.quiz.dto.response;

import com.project.ptittoanthu.common.base.entity.BaseEntity;
import com.project.ptittoanthu.quiz.model.QuizSize;
import com.project.ptittoanthu.quiz.model.QuizTime;
import com.project.ptittoanthu.quiz.model.QuizType;
import com.project.ptittoanthu.subjects.dto.response.SubjectResponse;
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
public class QuizResponse extends BaseEntity {
    private Integer id;
    private String title;
    private QuizType type;
    private QuizTime time;
    private QuizSize size;
    private UserResponse createBy;
    private SubjectResponse subject;
}
