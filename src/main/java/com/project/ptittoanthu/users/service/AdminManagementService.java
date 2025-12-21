package com.project.ptittoanthu.users.service;

import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.SearchRequest;
import com.project.ptittoanthu.subjects.dto.response.SubjectResponse;
import com.project.ptittoanthu.users.dto.SearchUserRequest;
import com.project.ptittoanthu.users.dto.request.UpdateUserRequest;
import com.project.ptittoanthu.users.dto.response.UserResponseDetail;
import jakarta.validation.Valid;

import javax.management.relation.RoleInfoNotFoundException;
import java.util.List;

public interface AdminManagementService {
    PageResult<List<UserResponseDetail>> getUsers(SearchUserRequest request);
    UserResponseDetail getUser(Integer id);
    UserResponseDetail updateUser(UpdateUserRequest request);
    void addTeacherToCourseSection(Integer teacherId, Integer subjectId) throws RoleInfoNotFoundException;
    void takeTeacherOutCourseSection(Integer teacherId, Integer subjectId);
    PageResult<List<SubjectResponse>> getTheSubjectsOfTeacher(Integer teacherId, @Valid SearchRequest searchRequest);
}
