package com.project.ptittoanthu.users.service;

import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.documents.dto.DocumentSearchRequest;
import com.project.ptittoanthu.documents.dto.response.DocumentResponse;
import com.project.ptittoanthu.quiz.dto.QuizResultSearchRequest;
import com.project.ptittoanthu.quiz.dto.QuizSearchRequest;
import com.project.ptittoanthu.quiz.dto.response.QuizResponse;
import com.project.ptittoanthu.quiz.dto.response.QuizResultResponse;
import com.project.ptittoanthu.subjects.dto.request.SubjectSearchRequest;
import com.project.ptittoanthu.subjects.dto.response.SubjectResponse;
import com.project.ptittoanthu.users.dto.request.ChangePasswordRequest;
import com.project.ptittoanthu.users.dto.request.UpdateProfileRequest;
import com.project.ptittoanthu.users.dto.response.UserResponse;
import com.project.ptittoanthu.users.dto.response.UserResponseDetail;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserResponseDetail getMe();

    UserResponseDetail updateProfile(UpdateProfileRequest profileRequest) throws IOException;

    void deleteProfile();

    UserResponse getOtherProfile(Integer id);

    void changePassword(ChangePasswordRequest request);
    PageResult<List<QuizResponse>> getMyQuizzes(QuizSearchRequest searchRequest);
    PageResult<List<QuizResultResponse>> getMyQuizResults(QuizResultSearchRequest searchRequest);
    PageResult<List<DocumentResponse>> getMyDocuments(DocumentSearchRequest searchRequest);
    PageResult<List<DocumentResponse>> getMyBookmarks(DocumentSearchRequest searchRequest);
    PageResult<List<SubjectResponse>> getMyFavoriteSubjects(SubjectSearchRequest searchRequest);
}
