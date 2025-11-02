package com.project.ptittoanthu.question.service.impl;

import com.project.ptittoanthu.question.dto.request.UpdateOptionRequest;
import com.project.ptittoanthu.question.dto.response.OptionResponse;
import com.project.ptittoanthu.question.dto.response.OptionResponseDetail;
import com.project.ptittoanthu.question.exception.OptionNotFoundExp;
import com.project.ptittoanthu.question.mapper.OptionMapper;
import com.project.ptittoanthu.question.model.Option;
import com.project.ptittoanthu.question.repository.OptionRepository;
import com.project.ptittoanthu.question.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;
    private final OptionMapper optionMapper;

    @Transactional
    @Override
    public OptionResponseDetail updateOption(UpdateOptionRequest request) {
        Option option = optionRepository.findById(request.getId())
                .orElseThrow(() -> new OptionNotFoundExp(""));

        optionMapper.updateOption(request, option);
        optionRepository.save(option);
        return optionMapper.toOptionResponseDetail(option);
    }

    @Override
    public List<OptionResponse> getOptionsResponseByQuestionId(Integer questionId) {
        List<Option> options = optionRepository.findAllByQuestionId(questionId);
        return optionMapper.toOptionResponse(options);
    }

    @Override
    public List<OptionResponseDetail> getOptionsResponseDetailByQuestionId(Integer questionId) {
        List<Option> options = optionRepository.findAllByQuestionId(questionId);
        return optionMapper.toOptionsResponseDetail(options);
    }

    @Transactional
    @Override
    public void deleteOption(Integer id) {
        optionRepository.deleteById(id);
    }
}
