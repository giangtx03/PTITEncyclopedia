package com.project.ptittoanthu.authentication.dto.response;

import com.project.ptittoanthu.authentication.model.Role;
import com.project.ptittoanthu.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserResponse extends BaseResponse {
    private String id;
    private String username;
    private String email;
    private String avatar;
    private String address;
    private LocalDate dob;
    private String phoneNumber;
    private Role role;
}
