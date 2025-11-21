package com.project.ptittoanthu.majors.dto;

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
public class UpdateMajorRequest {
    @Schema(name = "id", example = "1")
    @NotNull
    Integer id;
    @NotBlank
    @Schema(name = "name", example = "Multimedia")
    String name;
    @NotBlank
    @Schema(name = "code", example = "DPT")
    String code;
    @NotNull
    @Schema(name = "facultyId", example = "1")
    Integer facultyId;
}
