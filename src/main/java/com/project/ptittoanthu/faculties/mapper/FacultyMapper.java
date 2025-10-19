package com.project.ptittoanthu.faculties.mapper;

import com.project.ptittoanthu.faculties.dto.CreateFacultyRequest;
import com.project.ptittoanthu.faculties.dto.FacultyResponse;
import com.project.ptittoanthu.faculties.dto.UpdateFacultyRequest;
import com.project.ptittoanthu.faculties.model.Faculty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FacultyMapper {
    Faculty toFaculty(CreateFacultyRequest request);
    FacultyResponse toFacultyResponse(Faculty faculty);

    @Mapping(target = "id", ignore = true)
    void updateFaculty(UpdateFacultyRequest request, @MappingTarget Faculty faculty);
}
