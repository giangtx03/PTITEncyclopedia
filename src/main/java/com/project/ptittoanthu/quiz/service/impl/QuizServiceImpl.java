package com.project.ptittoanthu.quiz.service.impl;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.helper.MetaDataHelper;
import com.project.ptittoanthu.common.helper.SortHelper;
import com.project.ptittoanthu.common.util.SecurityUtils;
import com.project.ptittoanthu.question.model.Question;
import com.project.ptittoanthu.question.repository.QuestionRepository;
import com.project.ptittoanthu.quiz.dto.QuizSearchRequest;
import com.project.ptittoanthu.quiz.dto.request.CreateQuizRequest;
import com.project.ptittoanthu.quiz.dto.request.UpdateQuizRequest;
import com.project.ptittoanthu.quiz.dto.response.QuizResponse;
import com.project.ptittoanthu.quiz.dto.response.QuizResponseDetail;
import com.project.ptittoanthu.quiz.exception.QuizNotFoundExp;
import com.project.ptittoanthu.quiz.mapper.QuizMapper;
import com.project.ptittoanthu.quiz.model.Quiz;
import com.project.ptittoanthu.quiz.repository.QuizRepository;
import com.project.ptittoanthu.quiz.service.QuizService;
import com.project.ptittoanthu.subjects.exception.SubjectNotFoundException;
import com.project.ptittoanthu.subjects.model.Subject;
import com.project.ptittoanthu.subjects.repository.SubjectRepository;
import com.project.ptittoanthu.users.exception.UserNotFoundException;
import com.project.ptittoanthu.users.model.User;
import com.project.ptittoanthu.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final QuestionRepository questionRepository;
    private final QuizMapper quizMapper;

    @Override
    public QuizResponseDetail createQuiz(CreateQuizRequest request) {
        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found"));

        int limit = request.getSize().getNumberOfQuestions();
        Pageable pageable = PageRequest.of(0, limit);
        List<Question> randomQuestions = questionRepository.findRandomQuestionsBySubject(request.getSubjectId(), pageable);

        if (randomQuestions.size() < limit) {
            throw new IllegalArgumentException("Không đủ câu hỏi trong subject này");
        }

        Quiz quiz = quizMapper.toQuiz(request);
        quiz.setCreateBy(user);
        quiz.setSubject(subject);
        quiz.setQuestions(randomQuestions);
        quizRepository.save(quiz);
        return quizMapper.toQuizResponseDetail(quiz);
    }

    @Override
    public PageResult<List<QuizResponse>> getQuizzes(QuizSearchRequest request) {
        Sort sort = SortHelper.buildSort(request.getOrder(), request.getDirection());
        Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize(), sort);

        Page<Quiz> quizzes = quizRepository.findAllBySearchRequest(request.getKeyword() ,request.getSubjectId(),
                request.getType(), request.getTime(), pageable);
        List<QuizResponse> responses = quizMapper.toQuizResponse(quizzes.getContent());
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(quizzes, request);
        return PageResult.<List<QuizResponse>>builder()
                .metaDataResponse(metaDataResponse)
                .data(responses)
                .build();
    }

    @Override
    public QuizResponseDetail updateQuiz(UpdateQuizRequest request) {
        Quiz quiz = quizRepository.findById(request.getId())
                .orElseThrow(() -> new QuizNotFoundExp(""));

        quizMapper.updateQuiz(request, quiz);
        quizRepository.save(quiz);
        return quizMapper.toQuizResponseDetail(quiz);
    }

    @Override
    public void delete(Integer id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundExp(""));

        quiz.setDeletedAt(OffsetDateTime.now());
        quizRepository.save(quiz);
    }
}
