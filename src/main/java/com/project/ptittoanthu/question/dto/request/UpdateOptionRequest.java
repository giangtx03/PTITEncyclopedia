package com.project.ptittoanthu.question.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateOptionRequest {
    private Integer id;
    private String value;
    private boolean correct;
}
