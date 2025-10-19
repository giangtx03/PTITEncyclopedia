package com.project.ptittoanthu.majors.dto;

import com.project.ptittoanthu.common.base.dto.SearchRequest;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MajorSearchRequest extends SearchRequest {
    @Parameter(example = "1")
    Integer facultyId;
}
