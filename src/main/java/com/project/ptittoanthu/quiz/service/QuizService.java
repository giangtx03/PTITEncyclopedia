package com.project.ptittoanthu.quiz.service;

import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.quiz.dto.QuizSearchRequest;
import com.project.ptittoanthu.quiz.dto.request.CreateQuizRequest;
import com.project.ptittoanthu.quiz.dto.request.UpdateQuizRequest;
import com.project.ptittoanthu.quiz.dto.response.QuizResponse;
import com.project.ptittoanthu.quiz.dto.response.QuizResponseDetail;

import java.util.List;

public interface QuizService {
    QuizResponseDetail createQuiz(CreateQuizRequest request);
    QuizResponseDetail getQuiz(Integer id);
    PageResult<List<QuizResponse>> getQuizzes(QuizSearchRequest request);
    QuizResponseDetail updateQuiz(UpdateQuizRequest request);
    void delete(Integer id);
}
