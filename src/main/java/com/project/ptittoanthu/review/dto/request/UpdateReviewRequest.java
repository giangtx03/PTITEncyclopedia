package com.project.ptittoanthu.review.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
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
    @NotNull
    private Integer id;
    @Parameter(example = "very good")
    private String content;

    @Parameter(example = "5")
    @Min(value = 1)
    @Max(value = 5)
    private int star;
}
