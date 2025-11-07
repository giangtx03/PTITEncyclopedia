package com.project.ptittoanthu.quiz.mapper;

import com.project.ptittoanthu.question.mapper.OptionMapper;
import com.project.ptittoanthu.question.mapper.QuestionMapper;
import com.project.ptittoanthu.quiz.dto.request.CreateQuizResultItemRequest;
import com.project.ptittoanthu.quiz.dto.response.QuizResultItemResponse;
import com.project.ptittoanthu.quiz.model.QuizResultItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class, OptionMapper.class})
public interface QuizResultItemMapper {
    List<QuizResultItemResponse> toQuizResultItemResponse(List<QuizResultItem> quizResultItem);
    QuizResultItemResponse toQuizResultItemResponse(QuizResultItem quizResultItem);
}
