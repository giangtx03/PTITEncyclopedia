package com.project.ptittoanthu.question.service;

import com.project.ptittoanthu.question.dto.request.UpdateOptionRequest;
import com.project.ptittoanthu.question.dto.response.OptionResponse;
import com.project.ptittoanthu.question.dto.response.OptionResponseDetail;

import java.util.List;

public interface OptionService {
    OptionResponseDetail updateOption(UpdateOptionRequest request);
    List<OptionResponse> getOptionsResponseByQuestionId(Integer questionId);
    List<OptionResponseDetail> getOptionsResponseDetailByQuestionId(Integer questionId);
    void deleteOption(Integer id);
}
