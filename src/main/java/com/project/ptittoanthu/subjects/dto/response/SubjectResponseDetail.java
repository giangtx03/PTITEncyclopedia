package com.project.ptittoanthu.subjects.dto.response;

import com.project.ptittoanthu.majors.dto.MajorResponse;
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
public class SubjectResponseDetail extends SubjectResponse {
    MajorResponse major;
}
