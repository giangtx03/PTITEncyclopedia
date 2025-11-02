package com.project.ptittoanthu.subjects.dto.request;

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
    @NotBlank
    String name;
    @NotBlank
    String code;
    @NotNull
    Integer majorId;
}
