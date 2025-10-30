package com.project.ptittoanthu.subjects.mapper;

import com.project.ptittoanthu.majors.mapper.MajorMapper;
import com.project.ptittoanthu.subjects.dto.request.CreateSubjectRequest;
import com.project.ptittoanthu.subjects.dto.request.UpdateSubjectRequest;
import com.project.ptittoanthu.subjects.dto.response.SubjectResponse;
import com.project.ptittoanthu.subjects.dto.response.SubjectResponseDetail;
import com.project.ptittoanthu.subjects.model.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {MajorMapper.class})
public interface SubjectMapper {
    SubjectResponse toSubjectResponse(Subject subject);
    Subject toSubject(CreateSubjectRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "major", ignore = true)
    void updateSubject(UpdateSubjectRequest request, @MappingTarget Subject subject);

    SubjectResponseDetail toSubjectDetailResponse(Subject subject);
}
