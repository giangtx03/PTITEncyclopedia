package com.project.ptittoanthu.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateReviewRequest {
    @Schema(name = "content", example = "very good")
    private String content;

    @Schema(name = "star", example = "5")
    @Min(value = 1)
    @Max(value = 5)
    private int star;

    @Schema(name = "documentId", example = "1")
    @NotNull
    private Integer documentId;
}
