package com.project.ptittoanthu.majors.mapper;

import com.project.ptittoanthu.faculties.mapper.FacultyMapper;
import com.project.ptittoanthu.majors.dto.CreateMajorRequest;
import com.project.ptittoanthu.majors.dto.MajorResponse;
import com.project.ptittoanthu.majors.dto.MajorResponseDetail;
import com.project.ptittoanthu.majors.dto.UpdateMajorRequest;
import com.project.ptittoanthu.majors.model.Major;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {FacultyMapper.class})
public interface MajorMapper {
    Major toMajor(CreateMajorRequest request);
    MajorResponse toMajorResponse(Major major);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "faculty", ignore = true)
    void updateMajor(UpdateMajorRequest request, @MappingTarget Major major);

    MajorResponseDetail toMajorResponseDetail(Major major);
}
