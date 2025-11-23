package com.project.ptittoanthu.question.service.impl;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.helper.MetaDataHelper;
import com.project.ptittoanthu.common.util.SecurityUtils;
import com.project.ptittoanthu.infra.files.ExcelHelper;
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
import com.project.ptittoanthu.subjects.exception.SubjectNotFoundException;
import com.project.ptittoanthu.subjects.model.Subject;
import com.project.ptittoanthu.subjects.repository.SubjectRepository;
import com.project.ptittoanthu.users.exception.UserNotFoundException;
import com.project.ptittoanthu.users.model.User;
import com.project.ptittoanthu.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final QuestionMapper mapper;

    @Transactional
    @Override
    public QuestionResponseDetail createQuestion(CreateQuestionRequest request) {
        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(""));
        Question question = mapper.toEntity(request);

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new SubjectNotFoundException(""));

        List<Option> options = mapper.toOption(request.getOptions());
        options.forEach(o -> o.setQuestion(question));

        List<Tip> tips = mapper.toTip(request.getTips());
        tips.forEach(t -> t.setQuestion(question));

        question.setUser(user);
        question.setSubject(subject);
        question.setOptions(options);
        question.setTips(tips);
        questionRepository.save(question);
        return mapper.toQuestionResponseDetail(question);
    }

    @Transactional
    @Override
    public List<QuestionResponseDetail> createQuestionByExcelFile(Integer subjectId, MultipartFile file) throws BadRequestException {
        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(""));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(""));
        if (!ExcelHelper.hasExcelFormat(file)) {
            throw new BadRequestException("File format is not supported. Please use .xlsx");
        }
        try {
            List<Question> questions = ExcelHelper.excelToQuestions(file.getInputStream());
            questions.forEach(question -> {
                question.setSubject(subject);
                question.setUser(user);
            });
            questionRepository.saveAll(questions);
            return mapper.toQuestionResponseDetail(questions);
        } catch (IOException e) {
            throw new RuntimeException("Không thể lưu dữ liệu excel: " + e.getMessage());
        }
    }

    @Transactional
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

    @Transactional
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

        question.setDeletedAt(LocalDateTime.now());
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
