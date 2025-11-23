package com.project.ptittoanthu.quiz.service.impl;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.helper.MetaDataHelper;
import com.project.ptittoanthu.common.helper.SortHelper;
import com.project.ptittoanthu.common.util.SecurityUtils;
import com.project.ptittoanthu.question.exception.OptionNotFoundExp;
import com.project.ptittoanthu.question.exception.QuestionNotFoundExp;
import com.project.ptittoanthu.question.model.Option;
import com.project.ptittoanthu.question.model.Question;
import com.project.ptittoanthu.question.repository.OptionRepository;
import com.project.ptittoanthu.question.repository.QuestionRepository;
import com.project.ptittoanthu.quiz.dto.QuizResultSearchRequest;
import com.project.ptittoanthu.quiz.dto.request.CreateQuizResultRequest;
import com.project.ptittoanthu.quiz.dto.response.QuizResultResponse;
import com.project.ptittoanthu.quiz.dto.response.QuizResultResponseDetail;
import com.project.ptittoanthu.quiz.exception.QuizNotFoundExp;
import com.project.ptittoanthu.quiz.exception.QuizResultNotFoundExp;
import com.project.ptittoanthu.quiz.mapper.QuizResultItemMapper;
import com.project.ptittoanthu.quiz.mapper.QuizResultMapper;
import com.project.ptittoanthu.quiz.model.Quiz;
import com.project.ptittoanthu.quiz.model.QuizResult;
import com.project.ptittoanthu.quiz.model.QuizResultItem;
import com.project.ptittoanthu.quiz.model.QuizSize;
import com.project.ptittoanthu.quiz.repository.QuizRepository;
import com.project.ptittoanthu.quiz.repository.QuizResultRepository;
import com.project.ptittoanthu.quiz.service.QuizResultService;
import com.project.ptittoanthu.users.exception.UserNotFoundException;
import com.project.ptittoanthu.users.model.Role;
import com.project.ptittoanthu.users.model.User;
import com.project.ptittoanthu.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizResultServiceImpl implements QuizResultService {

    private final QuizRepository quizRepository;
    private final QuizResultRepository quizResultRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final UserRepository userRepository;
    private final QuizResultMapper quizResultMapper;

    @Transactional
    @Override
    public QuizResultResponse createQuizResult(CreateQuizResultRequest request) {
        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new QuizNotFoundExp("Quiz not found"));

        QuizResult quizResult = new QuizResult();
        quizResult.setQuiz(quiz);
        quizResult.setUser(user);
        List<QuizResultItem> quizResultItems = request.getQuizResultItemRequests()
                .stream()
                .map(quizResultItemRequest -> {
                    Question question = questionRepository.findById(quizResultItemRequest.getQuestionId())
                            .orElseThrow(() -> new QuestionNotFoundExp(""));
                    Option option = optionRepository.findById(quizResultItemRequest.getSelectedId())
                            .orElseThrow(() -> new OptionNotFoundExp(""));
                    return QuizResultItem.builder()
                            .question(question)
                            .quizResult(quizResult)
                            .selected(option)
                            .build();
                })
                .toList();
        quizResult.setQuizResultItems(quizResultItems);
        quizResult.setScore(calculateScore(quizResultItems, quiz.getSize()));
        quizResultRepository.save(quizResult);
        return quizResultMapper.toQuizResultResponse(quizResult);
    }

    @Override
    public QuizResultResponseDetail getQuizResult(Integer id) {
        QuizResult quizResult = quizResultRepository.findById(id)
                .orElseThrow(() -> new QuizResultNotFoundExp(""));
        return quizResultMapper.toQuizResultResponseDetail(quizResult);
    }

    private float calculateScore(List<QuizResultItem> items, QuizSize size) {
        if (items == null || items.isEmpty() || size == null || size.getNumberOfQuestions() == 0)
            return 0f;

        long correctCount = items.stream()
                .filter(item -> item.getQuestion() != null) // câu hỏi tồn tại
                .filter(item -> item.getSelected() != null)  // có chọn đáp án
                .filter(item ->
                        // kiểm tra selected thuộc về chính question này
                        item.getSelected().getQuestion() != null &&
                                item.getSelected().getQuestion().getId().equals(item.getQuestion().getId())
                )
                .filter(item -> item.getSelected().isCorrect()) // đáp án đúng
                .count();

        return (float) correctCount / size.getNumberOfQuestions();
    }


    @Override
    public PageResult<List<QuizResultResponse>> getQuizResults(QuizResultSearchRequest request) {
        String email = SecurityUtils.getUserEmailFromSecurity();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));
        Integer userId = currentUser.getRole().equals(Role.STUDENT)
                ? currentUser.getId()
                : null;
        Sort sort = SortHelper.buildSort("qr."+request.getOrder(), request.getDirection());
        Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize(), sort);

        Page<QuizResult> page = quizResultRepository.findAllBySearch(
                request.getKeyword(),
                request.getSubjectId(),
                userId,
                pageable
        );

        List<QuizResultResponse> responses = quizResultMapper.toQuizResultResponse(page.getContent());
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(page, request);
        return  PageResult.<List<QuizResultResponse>>builder()
                .metaDataResponse(metaDataResponse)
                .data(responses)
                .build();
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        QuizResult quizResult = quizResultRepository.findById(id)
                .orElseThrow(() -> new QuizResultNotFoundExp(""));
        quizResult.setDeletedAt(LocalDateTime.now());
        quizResultRepository.save(quizResult);
    }
}
