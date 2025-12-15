package com.project.ptittoanthu.question.dto.response;

import com.project.ptittoanthu.common.base.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class QuestionResponse extends BaseResponse {
    private Integer id;
    private String content;
    private List<OptionResponseDetail> options;
}
