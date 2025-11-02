package com.project.ptittoanthu.question.service;

import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.question.dto.QuestionSearchRequest;
import com.project.ptittoanthu.question.dto.request.CreateOptionRequest;
import com.project.ptittoanthu.question.dto.request.CreateQuestionRequest;
import com.project.ptittoanthu.question.dto.request.CreateTipRequest;
import com.project.ptittoanthu.question.dto.request.UpdateQuestionRequest;
import com.project.ptittoanthu.question.dto.response.QuestionResponse;
import com.project.ptittoanthu.question.dto.response.QuestionResponseDetail;

import java.util.List;

public interface QuestionService {
    QuestionResponseDetail createQuestion(CreateQuestionRequest request);
    QuestionResponseDetail addTip(Integer questionId ,CreateTipRequest request);
    QuestionResponseDetail addOption(Integer questionId , CreateOptionRequest request);
    QuestionResponseDetail updateQuestion(UpdateQuestionRequest request);
    void deleteQuestion(Integer id);
    PageResult<List<QuestionResponse>> getQuestions(QuestionSearchRequest searchRequest);
}
