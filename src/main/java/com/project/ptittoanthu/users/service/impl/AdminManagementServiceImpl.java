package com.project.ptittoanthu.users.service.impl;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.helper.MetaDataHelper;
import com.project.ptittoanthu.common.helper.SortHelper;
import com.project.ptittoanthu.configs.db.FilterInterceptor;
import com.project.ptittoanthu.subjects.exception.SubjectNotFoundException;
import com.project.ptittoanthu.subjects.model.Subject;
import com.project.ptittoanthu.subjects.repository.SubjectRepository;
import com.project.ptittoanthu.users.dto.SearchUserRequest;
import com.project.ptittoanthu.users.dto.request.UpdateUserRequest;
import com.project.ptittoanthu.users.dto.response.UserResponseDetail;
import com.project.ptittoanthu.users.exception.UserNotFoundException;
import com.project.ptittoanthu.users.mapper.UserMapper;
import com.project.ptittoanthu.users.model.Role;
import com.project.ptittoanthu.users.model.User;
import com.project.ptittoanthu.users.repository.UserRepository;
import com.project.ptittoanthu.users.service.AdminManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleInfoNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminManagementServiceImpl implements AdminManagementService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SubjectRepository subjectRepository;
    private final FilterInterceptor filterInterceptor;

    @Override
    public PageResult<List<UserResponseDetail>> getUsers(SearchUserRequest request) {
        Sort sort = SortHelper.buildSort(request.getOrder(), request.getDirection());
        Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize(), sort);

        filterInterceptor.disableFilterForCurrentThread();
        Page<User> page = userRepository.findAllBySearchRequest(request.getKeyword(),
                request.getActive(), request.getLocked(), request.getRole(), pageable);
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(page, request);
        List<UserResponseDetail> responses = page.getContent().stream()
                .map(userMapper::toResponse).toList();
        return PageResult.<List<UserResponseDetail>>builder()
                .metaDataResponse(metaDataResponse)
                .data(responses)
                .build();
    }

    @Override
    public UserResponseDetail getUser(Integer id) {
        filterInterceptor.disableFilterForCurrentThread();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(""));
        return userMapper.toResponse(user);
    }

    @Transactional
    @Override
    public UserResponseDetail updateUser(UpdateUserRequest request) {
        filterInterceptor.disableFilterForCurrentThread();
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new UserNotFoundException(""));

        user.setRole(request.getRole());
        user.setLocked(request.isLocked());
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Transactional
    @Override
    public void addTeacherToCourseSection(Integer teacherId, Integer subjectId) throws RoleInfoNotFoundException {
        User user = userRepository.findById(teacherId)
                .orElseThrow(() -> new UserNotFoundException(""));
        if(user.getRole() != Role.TEACHER)
            throw new RoleInfoNotFoundException("");
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(""));

        user.getSubjects().add(subject);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void takeTeacherOutCourseSection(Integer teacherId, Integer subjectId) {
        User user = userRepository.findById(teacherId)
                .orElseThrow(() -> new UserNotFoundException(""));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(""));

        user.getSubjects().remove(subject);
        userRepository.save(user);
    }
}
