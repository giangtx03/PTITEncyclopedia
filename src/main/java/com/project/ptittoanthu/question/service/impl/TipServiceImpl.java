package com.project.ptittoanthu.question.service.impl;

import com.project.ptittoanthu.question.dto.request.UpdateTipRequest;
import com.project.ptittoanthu.question.dto.response.TipResponse;
import com.project.ptittoanthu.question.exception.TipNotFoundExp;
import com.project.ptittoanthu.question.mapper.TipMapper;
import com.project.ptittoanthu.question.model.Tip;
import com.project.ptittoanthu.question.repository.TipRepository;
import com.project.ptittoanthu.question.service.TipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipServiceImpl implements TipService {

    private final TipRepository tipRepository;
    private final TipMapper tipMapper;

    @Transactional
    @Override
    public TipResponse updateTip(UpdateTipRequest request) {
        Tip tip = tipRepository.findById(request.getId())
                .orElseThrow(() -> new TipNotFoundExp(""));

        tipMapper.updateTip(request, tip);
        tipRepository.save(tip);
        return tipMapper.toTipResponse(tip);
    }

    @Override
    public List<TipResponse> getTipsByQuestionId(Integer questionId) {
        List<Tip> tips = tipRepository.findAllByQuestionId(questionId);
        return tipMapper.toTipResponses(tips);
    }

    @Transactional
    @Override
    public void deleteTip(Integer id) {
        tipRepository.deleteById(id);
    }
}
