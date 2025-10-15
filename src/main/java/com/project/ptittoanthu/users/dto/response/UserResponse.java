package com.project.ptittoanthu.users.dto.response;

import com.project.ptittoanthu.users.model.Role;
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
public class UserResponse extends UserBaseResponse {
    private String email;
    private String address;
    private LocalDate dob;
    private String phoneNumber;
    private Role role;
}
