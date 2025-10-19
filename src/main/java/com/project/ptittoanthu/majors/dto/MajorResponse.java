package com.project.ptittoanthu.majors.dto;

import com.project.ptittoanthu.common.base.dto.BaseResponse;
import com.project.ptittoanthu.faculties.dto.FacultyResponse;
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
public class MajorResponse extends BaseResponse {
    Integer id;
    String name;
    String code;
    FacultyResponse faculty;
}
