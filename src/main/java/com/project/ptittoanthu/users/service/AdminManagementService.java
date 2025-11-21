package com.project.ptittoanthu.users.service;

import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.users.dto.SearchUserRequest;
import com.project.ptittoanthu.users.dto.request.UpdateUserRequest;
import com.project.ptittoanthu.users.dto.response.UserResponse;
import com.project.ptittoanthu.users.dto.response.UserResponseDetail;

import javax.management.relation.RoleInfoNotFoundException;
import java.util.List;

public interface AdminManagementService {
    PageResult<List<UserResponse>> getUsers(SearchUserRequest request);
    UserResponseDetail getUser(Integer id);
    UserResponseDetail updateUser(UpdateUserRequest request);
    void addTeacherToCourseSection(Integer teacherId, Integer subjectId) throws RoleInfoNotFoundException;
    void takeTeacherOutCourseSection(Integer teacherId, Integer subjectId);
}
