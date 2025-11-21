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
public class UpdateReviewRequest {
    @Schema(name = "id", example = "1")
    @NotNull
    private Integer id;
    @Schema(name = "content", example = "very good")
    private String content;

    @Schema(name = "star", example = "5")
    @Min(value = 1)
    @Max(value = 5)
    private int star;
}
