package com.project.ptittoanthu.users.service;

import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.SearchRequest;
import com.project.ptittoanthu.subjects.dto.response.SubjectResponse;

import java.util.List;

public interface TeacherManagementService {
    PageResult<List<SubjectResponse>> getTheSubjectsOfTeacher(SearchRequest request);
    void enrollToSubject(Integer subjectId);
    void takeOutSubject(Integer subjectId);
}
