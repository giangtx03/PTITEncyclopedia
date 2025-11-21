package com.project.ptittoanthu.question.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateTipRequest {
    @Schema(name = "id", example = "1")
    private Integer id;
    @Schema(name = "content", example = "Gợi ý ví dụ!")
    private String content;
}
