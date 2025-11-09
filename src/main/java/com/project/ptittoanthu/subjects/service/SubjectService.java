package com.project.ptittoanthu.subjects.service;

import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.subjects.dto.request.CreateSubjectRequest;
import com.project.ptittoanthu.subjects.dto.request.SubjectSearchRequest;
import com.project.ptittoanthu.subjects.dto.request.UpdateSubjectRequest;
import com.project.ptittoanthu.subjects.dto.response.SubjectResponse;
import com.project.ptittoanthu.subjects.dto.response.SubjectResponseDetail;

import java.util.List;

public interface SubjectService {
    SubjectResponseDetail createSubject(CreateSubjectRequest request);
    SubjectResponseDetail updateSubject(UpdateSubjectRequest request);
    PageResult<List<SubjectResponse>> getSubjects(SubjectSearchRequest searchRequest);
    void joinSubject(Integer subjectId);
    void leaveSubject(Integer subjectId);
    SubjectResponseDetail getSubject(Integer id);
    void deleteSubject(Integer id);
}
