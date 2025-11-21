package com.project.ptittoanthu.users.dto;

import com.project.ptittoanthu.common.base.dto.SearchRequest;
import com.project.ptittoanthu.users.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SearchUserRequest extends SearchRequest {
    @Schema(name = "role", example = "ADMIN")
    private Role role;
    @Schema(name = "locked", example = "true")
    private Boolean locked;
    @Schema(name = "active", example = "true")
    private Boolean active;
}
