package com.project.ptittoanthu.question.dto.response;

import com.project.ptittoanthu.common.base.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class TipResponse extends BaseResponse {
    private String content;
}
