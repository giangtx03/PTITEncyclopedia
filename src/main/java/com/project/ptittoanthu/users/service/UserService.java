package com.project.ptittoanthu.users.service;

import com.project.ptittoanthu.users.dto.request.ChangePasswordRequest;
import com.project.ptittoanthu.users.dto.request.UpdateProfileRequest;
import com.project.ptittoanthu.users.dto.response.UserBaseResponse;
import com.project.ptittoanthu.users.dto.response.UserResponse;

import java.io.IOException;

public interface UserService {
    UserResponse getMe();

    UserResponse updateProfile(UpdateProfileRequest profileRequest) throws IOException;

    void deleteProfile();

    UserBaseResponse getOtherProfile(Integer id);

    void changePassword(ChangePasswordRequest request);
}
