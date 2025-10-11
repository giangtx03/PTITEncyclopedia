package com.project.ptittoanthu.authentication.mapper;

import com.project.ptittoanthu.authentication.dto.request.RegisterRequest;
import com.project.ptittoanthu.authentication.dto.response.UserResponse;
import com.project.ptittoanthu.authentication.model.Role;
import com.project.ptittoanthu.authentication.model.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", imports = {Role.class})
public interface UserMapper {
    UserResponse toResponse(User user);

    @Mapping(target = "role", expression = "java(Role.STUDENT)")
    @Mapping(target = "password", expression =
            "java(passwordEncoder.encode(registerRequest.getPassword()))")
    User toUser(RegisterRequest registerRequest,
                @Context PasswordEncoder passwordEncoder);
}
