package com.project.ptittoanthu.quiz.service;

import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.quiz.dto.QuizResultSearchRequest;
import com.project.ptittoanthu.quiz.dto.request.CreateQuizResultRequest;
import com.project.ptittoanthu.quiz.dto.response.QuizResultResponse;
import com.project.ptittoanthu.quiz.dto.response.QuizResultResponseDetail;

import java.util.List;

public interface QuizResultService {
    QuizResultResponseDetail createQuizResult(CreateQuizResultRequest request);
    QuizResultResponseDetail getQuizResult(Integer id);
    PageResult<List<QuizResultResponse>> getQuizResults(QuizResultSearchRequest request);
    void delete(Integer id);
}
