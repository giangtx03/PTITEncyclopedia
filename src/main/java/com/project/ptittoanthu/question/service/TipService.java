package com.project.ptittoanthu.question.service;

import com.project.ptittoanthu.question.dto.request.UpdateTipRequest;
import com.project.ptittoanthu.question.dto.response.TipResponse;

import java.util.List;

public interface TipService {
    TipResponse updateTip(UpdateTipRequest request);
    List<TipResponse> getTipsByQuestionId(Integer questionId);
    void deleteTip(Integer id);
}
