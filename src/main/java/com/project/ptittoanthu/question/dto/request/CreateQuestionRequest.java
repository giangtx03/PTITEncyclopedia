package com.project.ptittoanthu.question.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateQuestionRequest {
    @NotBlank
    @Schema(description = "Nhập nội dung câu hỏi")
    private String content;

    @NotBlank
    @Schema(description = "Nhập môn học")
    private Integer subjectId;

    @NotEmpty
    private List<CreateOptionRequest> options;
    private List<CreateTipRequest> tips;
}
