package com.project.ptittoanthu.users.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class UserResponseDetail extends UserResponse {
    private String email;
    private String address;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dob;
    private String phoneNumber;
    private Role role;
    private boolean active;
    private boolean locked;
}
