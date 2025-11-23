package com.project.ptittoanthu.users.service.impl;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.helper.MetaDataHelper;
import com.project.ptittoanthu.common.helper.SortHelper;
import com.project.ptittoanthu.common.util.SecurityUtils;
import com.project.ptittoanthu.documents.dto.DocumentSearchRequest;
import com.project.ptittoanthu.documents.dto.DocumentStatsDto;
import com.project.ptittoanthu.documents.dto.response.DocumentResponse;
import com.project.ptittoanthu.documents.mapper.DocumentMapper;
import com.project.ptittoanthu.documents.repository.DocumentRepository;
import com.project.ptittoanthu.infra.files.FileService;
import com.project.ptittoanthu.quiz.dto.QuizResultSearchRequest;
import com.project.ptittoanthu.quiz.dto.QuizSearchRequest;
import com.project.ptittoanthu.quiz.dto.response.QuizResponse;
import com.project.ptittoanthu.quiz.dto.response.QuizResultResponse;
import com.project.ptittoanthu.quiz.mapper.QuizMapper;
import com.project.ptittoanthu.quiz.mapper.QuizResultMapper;
import com.project.ptittoanthu.quiz.model.Quiz;
import com.project.ptittoanthu.quiz.model.QuizResult;
import com.project.ptittoanthu.quiz.repository.QuizRepository;
import com.project.ptittoanthu.quiz.repository.QuizResultRepository;
import com.project.ptittoanthu.subjects.dto.request.SubjectSearchRequest;
import com.project.ptittoanthu.subjects.dto.response.SubjectResponse;
import com.project.ptittoanthu.subjects.mapper.SubjectMapper;
import com.project.ptittoanthu.subjects.model.Subject;
import com.project.ptittoanthu.subjects.repository.SubjectRepository;
import com.project.ptittoanthu.users.dto.request.ChangePasswordRequest;
import com.project.ptittoanthu.users.dto.request.UpdateProfileRequest;
import com.project.ptittoanthu.users.dto.response.UserResponse;
import com.project.ptittoanthu.users.dto.response.UserResponseDetail;
import com.project.ptittoanthu.users.exception.PasswordNotMatches;
import com.project.ptittoanthu.users.exception.UserNotFoundException;
import com.project.ptittoanthu.users.mapper.UserMapper;
import com.project.ptittoanthu.users.model.User;
import com.project.ptittoanthu.users.repository.UserRepository;
import com.project.ptittoanthu.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;
    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;
    private final QuizResultRepository quizResultRepository;
    private final QuizResultMapper quizResultMapper;
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public UserResponseDetail getMe() {
        String email = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(""));

        return userMapper.toResponse(user);
    }

    @Transactional
    @Override
    public UserResponseDetail updateProfile(UpdateProfileRequest profileRequest) throws IOException {
        String email = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(""));

        if (profileRequest.getAvatar() != null && !profileRequest.getAvatar().isEmpty()) {
            String fileName = fileService.upload(profileRequest.getAvatar(), null);
            fileService.delete(user.getAvatar());
            user.setAvatar(fileName);
        }

        userMapper.updateUser(profileRequest, user);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Transactional
    @Override
    public void deleteProfile() {
        String email = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(""));
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public UserResponse getOtherProfile(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(""));
        return userMapper.toBaseResponse(user);
    }

    @Transactional
    @Override
    public void changePassword(ChangePasswordRequest request) {
        String email = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(""));

        if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            return;
        }
        throw new PasswordNotMatches("");
    }

    @Override
    public PageResult<List<QuizResponse>> getMyQuizzes(QuizSearchRequest request) {
        Sort sort = SortHelper.buildSort("q." + request.getOrder(), request.getDirection());
        Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize(), sort);

        String email = SecurityUtils.getUserEmailFromSecurity();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));

        Page<Quiz> quizzes = quizRepository.findAllBySearchRequest(request.getKeyword(),
                currentUser.getId(), request.getSubjectId(),
                request.getType(), request.getTime(), pageable);
        List<QuizResponse> responses = quizMapper.toQuizResponse(quizzes.getContent());
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(quizzes, request);
        return PageResult.<List<QuizResponse>>builder()
                .metaDataResponse(metaDataResponse)
                .data(responses)
                .build();
    }

    @Override
    public PageResult<List<QuizResultResponse>> getMyQuizResults(QuizResultSearchRequest request) {
        Sort sort = SortHelper.buildSort("qr." + request.getOrder(), request.getDirection());
        Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize(), sort);

        String email = SecurityUtils.getUserEmailFromSecurity();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));

        Page<QuizResult> page = quizResultRepository.findAllBySearch(
                request.getKeyword(),
                request.getSubjectId(),
                currentUser.getId(),
                pageable
        );

        List<QuizResultResponse> responses = quizResultMapper.toQuizResultResponse(page.getContent());
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(page, request);
        return PageResult.<List<QuizResultResponse>>builder()
                .metaDataResponse(metaDataResponse)
                .data(responses)
                .build();
    }

    @Override
    public PageResult<List<DocumentResponse>> getMyDocuments(DocumentSearchRequest request) {
        Sort sort = SortHelper.buildSort("d." + request.getOrder(), request.getDirection());
        Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize(), sort);

        String email = SecurityUtils.getUserEmailFromSecurity();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));

        Page<DocumentStatsDto> documents = documentRepository.findAllWithStatsDto(request.getKeyword(), currentUser.getId(), request.getSubjectId(), pageable);

        List<DocumentResponse> responses = documentMapper.toDocumentResponse(documents.getContent());
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(documents, request);
        return PageResult.<List<DocumentResponse>>builder()
                .metaDataResponse(metaDataResponse)
                .data(responses)
                .build();
    }

    @Override
    public PageResult<List<DocumentResponse>> getMyBookmarks(DocumentSearchRequest request) {
        Sort sort = SortHelper.buildSort("d." + request.getOrder(), request.getDirection());
        Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize(), sort);

        String email = SecurityUtils.getUserEmailFromSecurity();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));

        Page<DocumentStatsDto> documents = documentRepository.findAllWithStatsDtoWithBookmark(request.getKeyword(),
                currentUser.getId(), request.getSubjectId(), pageable);

        List<DocumentResponse> responses = documentMapper.toDocumentResponse(documents.getContent());
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(documents, request);
        return PageResult.<List<DocumentResponse>>builder()
                .metaDataResponse(metaDataResponse)
                .data(responses)
                .build();
    }

    @Override
    public PageResult<List<SubjectResponse>> getMyFavoriteSubjects(SubjectSearchRequest request) {
        Sort sort = SortHelper.buildSort("s." + request.getOrder(), request.getDirection());
        Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize(), sort);

        String email = SecurityUtils.getUserEmailFromSecurity();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng"));

        Page<Subject> subjectPage = subjectRepository.findAllBySearchRequestFavorite(request.getMajorId(),
                currentUser.getId(), request.getKeyword(), pageable);
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(subjectPage, request);
        List<SubjectResponse> subjectResponses = subjectPage.getContent().stream().map(
                subjectMapper::toSubjectResponse
        ).toList();
        return PageResult.<List<SubjectResponse>>builder()
                .metaDataResponse(metaDataResponse)
                .data(subjectResponses)
                .build();
    }
}