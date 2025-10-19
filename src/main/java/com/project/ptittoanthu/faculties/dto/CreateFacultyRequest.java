package com.project.ptittoanthu.faculties.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateFacultyRequest {
    @NotBlank
    String name;
    @NotBlank
    String code;
}
