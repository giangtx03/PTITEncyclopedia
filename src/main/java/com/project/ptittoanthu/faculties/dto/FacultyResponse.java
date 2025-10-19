package com.project.ptittoanthu.faculties.dto;

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
public class FacultyResponse extends BaseResponse {
    Integer id;
    String name;
    String code;
}
