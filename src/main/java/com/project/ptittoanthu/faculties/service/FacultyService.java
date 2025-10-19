package com.project.ptittoanthu.faculties.service;

import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.SearchRequest;
import com.project.ptittoanthu.faculties.dto.CreateFacultyRequest;
import com.project.ptittoanthu.faculties.dto.FacultyResponse;
import com.project.ptittoanthu.faculties.dto.UpdateFacultyRequest;

import java.util.List;

public interface FacultyService {
    FacultyResponse createFaculty(CreateFacultyRequest request);
    FacultyResponse updateFaculty(UpdateFacultyRequest request);
    PageResult<List<FacultyResponse>> getFaculties(SearchRequest searchRequest);
    FacultyResponse getFaculty(Integer id);
    void deleteFaculty(Integer id);
}
