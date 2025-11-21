package com.project.ptittoanthu.users.dto.request;

import com.project.ptittoanthu.users.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    @Schema(name = "id", example = "1")
    @NotNull
    private Integer id;
    @Schema(name = "role", example = "ADMIN")
    @NotNull
    private Role role;
    @Schema(name = "locked", example = "false")
    @NotNull
    private boolean locked;
}
