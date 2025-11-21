package com.project.ptittoanthu.subjects.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateSubjectRequest {
    @Schema(name = "name", example = "DSA")
    @NotBlank
    String name;
    @Schema(name = "code", example = "INT1")
    @NotBlank
    String code;
    @Schema(name = "majorId", example = "1")
    @NotNull
    Integer majorId;
}
