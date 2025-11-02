package com.project.ptittoanthu.users.service;

import com.project.ptittoanthu.users.dto.request.ChangePasswordRequest;
import com.project.ptittoanthu.users.dto.request.UpdateProfileRequest;
import com.project.ptittoanthu.users.dto.response.UserResponse;
import com.project.ptittoanthu.users.dto.response.UserResponseDetail;

import java.io.IOException;

public interface UserService {
    UserResponseDetail getMe();

    UserResponseDetail updateProfile(UpdateProfileRequest profileRequest) throws IOException;

    void deleteProfile();

    UserResponse getOtherProfile(Integer id);

    void changePassword(ChangePasswordRequest request);
}
