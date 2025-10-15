package com.project.ptittoanthu.users.mapper;

import com.project.ptittoanthu.users.dto.request.RegisterRequest;
import com.project.ptittoanthu.users.dto.request.UpdateProfileRequest;
import com.project.ptittoanthu.users.dto.response.UserBaseResponse;
import com.project.ptittoanthu.users.dto.response.UserResponse;
import com.project.ptittoanthu.users.model.Role;
import com.project.ptittoanthu.users.model.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring", imports = {Role.class})
public interface UserMapper {
    UserResponse toResponse(User user);

    UserBaseResponse toBaseResponse(User user);

    @Mapping(target = "role", expression = "java(Role.STUDENT)")
    @Mapping(target = "password", expression =
            "java(passwordEncoder.encode(registerRequest.getPassword()))")
    User toUser(RegisterRequest registerRequest,
                @Context PasswordEncoder passwordEncoder);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    void updateUser(UpdateProfileRequest request, @MappingTarget User user);
}
