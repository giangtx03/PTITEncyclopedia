package com.project.ptittoanthu.faculties.dto;

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
public class UpdateFacultyRequest {
    @NotNull
    Integer id;
    @NotBlank
    String name;
    @NotBlank
    String code;
}
