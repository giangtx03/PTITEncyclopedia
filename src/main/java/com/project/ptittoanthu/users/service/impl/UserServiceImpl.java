package com.project.ptittoanthu.users.service.impl;

import com.project.ptittoanthu.common.util.SecurityUtils;
import com.project.ptittoanthu.infra.images.FileService;
import com.project.ptittoanthu.users.dto.request.ChangePasswordRequest;
import com.project.ptittoanthu.users.dto.request.UpdateProfileRequest;
import com.project.ptittoanthu.users.dto.response.UserResponse;
import com.project.ptittoanthu.users.exception.PasswordNotMatches;
import com.project.ptittoanthu.users.exception.UserNotFoundException;
import com.project.ptittoanthu.users.mapper.UserMapper;
import com.project.ptittoanthu.users.model.User;
import com.project.ptittoanthu.users.repository.UserRepository;
import com.project.ptittoanthu.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Qualifier("image-local")
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getMe() {
        String email = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(""));

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateProfile(UpdateProfileRequest profileRequest) throws IOException {
        String email = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(""));

        if (profileRequest.getAvatar() != null && !profileRequest.getAvatar().isEmpty()) {
            String fileName = fileService.upload(profileRequest.getAvatar());
            fileService.delete(user.getAvatar());
            user.setAvatar(fileName);
        }

        userMapper.updateUser(profileRequest, user);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    public void deleteProfile() {
        String email = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(""));
        user.setDeletedAt(OffsetDateTime.now());
        userRepository.save(user);
    }

    @Override
    public UserResponse getOtherProfile(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(""));
        return userMapper.toResponse(user);
    }

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
}
