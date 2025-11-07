package com.project.ptittoanthu.quiz.mapper;

import com.project.ptittoanthu.quiz.dto.response.QuizResultResponse;
import com.project.ptittoanthu.quiz.dto.response.QuizResultResponseDetail;
import com.project.ptittoanthu.quiz.model.QuizResult;
import com.project.ptittoanthu.users.mapper.UserMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {QuizMapper.class, UserMapper.class, QuizResultItemMapper.class})
public interface QuizResultMapper {
    List<QuizResultResponse> toQuizResultResponse(List<QuizResult> quizResults);
    QuizResultResponse toQuizResultResponse(QuizResult quizResult);

    QuizResultResponseDetail toQuizResultResponseDetail(QuizResult quizResult);
}
