package com.project.ptittoanthu.question.service.impl;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.helper.MetaDataHelper;
import com.project.ptittoanthu.common.util.SecurityUtils;
import com.project.ptittoanthu.question.dto.QuestionSearchRequest;
import com.project.ptittoanthu.question.dto.request.CreateOptionRequest;
import com.project.ptittoanthu.question.dto.request.CreateQuestionRequest;
import com.project.ptittoanthu.question.dto.request.CreateTipRequest;
import com.project.ptittoanthu.question.dto.request.UpdateQuestionRequest;
import com.project.ptittoanthu.question.dto.response.QuestionResponse;
import com.project.ptittoanthu.question.dto.response.QuestionResponseDetail;
import com.project.ptittoanthu.question.exception.QuestionNotFoundExp;
import com.project.ptittoanthu.question.mapper.QuestionMapper;
import com.project.ptittoanthu.question.model.Option;
import com.project.ptittoanthu.question.model.Question;
import com.project.ptittoanthu.question.model.Tip;
import com.project.ptittoanthu.question.repository.QuestionRepository;
import com.project.ptittoanthu.question.service.QuestionService;
import com.project.ptittoanthu.users.exception.UserNotFoundException;
import com.project.ptittoanthu.users.model.User;
import com.project.ptittoanthu.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final QuestionMapper mapper;

    @Transactional
    @Override
    public QuestionResponseDetail createQuestion(CreateQuestionRequest request) {
        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(""));
        Question question = mapper.toEntity(request);
        question.setUser(user);
        questionRepository.save(question);

        List<Option> options = mapper.toOption(request.getOptions());
        options.forEach(o -> o.setQuestion(question));

        List<Tip> tips = mapper.toTip(request.getTips());
        tips.forEach(t -> t.setQuestion(question));

        question.setOptions(options);
        question.setTips(tips);
        questionRepository.save(question);
        return mapper.toQuestionResponseDetail(question);
    }

    @Override
    public QuestionResponseDetail addTip(Integer questionId, CreateTipRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundExp("Not found question"));

        Tip tip = mapper.toTip(request);
        tip.setQuestion(question);
        question.getTips().add(tip);
        questionRepository.save(question);
        return mapper.toQuestionResponseDetail(question);
    }

    @Override
    public QuestionResponseDetail addOption(Integer questionId, CreateOptionRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundExp("Not found question"));

        Option option = mapper.toOption(request);
        option.setQuestion(question);
        question.getOptions().add(option);
        questionRepository.save(question);
        return mapper.toQuestionResponseDetail(question);
    }

    @Transactional
    @Override
    public QuestionResponseDetail updateQuestion(UpdateQuestionRequest request) {
        Question question = questionRepository.findById(request.getId())
                .orElseThrow(() -> new QuestionNotFoundExp("Not found question"));

        mapper.updateEntity(request, question);
        questionRepository.save(question);
        return mapper.toQuestionResponseDetail(question);
    }

    @Transactional
    @Override
    public void deleteQuestion(Integer id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundExp("Not found question"));

        question.setDeletedAt(OffsetDateTime.now());
        questionRepository.save(question);
    }

    @Override
    public PageResult<List<QuestionResponse>> getQuestions(QuestionSearchRequest searchRequest) {
        Pageable pageable = PageRequest.of(searchRequest.getCurrentPage() - 1, searchRequest.getPageSize());
        Page<Question> questions = questionRepository.findAllBySearchRequest(searchRequest.getQuizId(), pageable);

        List<QuestionResponse> responses = mapper.toQuestionResponse(questions.getContent());
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(questions, searchRequest);
        return PageResult.<List<QuestionResponse>>builder()
                .metaDataResponse(metaDataResponse)
                .data(responses)
                .build();
    }
}
