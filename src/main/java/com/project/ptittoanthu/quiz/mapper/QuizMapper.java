package com.project.ptittoanthu.quiz.mapper;

import com.project.ptittoanthu.question.mapper.QuestionMapper;
import com.project.ptittoanthu.quiz.dto.request.CreateQuizRequest;
import com.project.ptittoanthu.quiz.dto.request.UpdateQuizRequest;
import com.project.ptittoanthu.quiz.dto.response.QuizResponse;
import com.project.ptittoanthu.quiz.dto.response.QuizResponseDetail;
import com.project.ptittoanthu.quiz.model.Quiz;
import com.project.ptittoanthu.subjects.mapper.SubjectMapper;
import com.project.ptittoanthu.users.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class, UserMapper.class, SubjectMapper.class})
public interface QuizMapper {
    Quiz toQuiz(CreateQuizRequest request);

    List<QuizResponse> toQuizResponse(List<Quiz> quizzes);
    QuizResponse toQuizResponse(Quiz quiz);

    QuizResponseDetail toQuizResponseDetail(Quiz quiz);

    @Mapping(target = "id", ignore = true)
    void updateQuiz(UpdateQuizRequest request, @MappingTarget Quiz quiz);
}
