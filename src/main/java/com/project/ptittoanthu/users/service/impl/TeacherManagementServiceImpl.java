package com.project.ptittoanthu.users.service.impl;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.SearchRequest;
import com.project.ptittoanthu.common.helper.MetaDataHelper;
import com.project.ptittoanthu.common.helper.SortHelper;
import com.project.ptittoanthu.common.util.SecurityUtils;
import com.project.ptittoanthu.subjects.dto.response.SubjectResponse;
import com.project.ptittoanthu.subjects.exception.SubjectNotFoundException;
import com.project.ptittoanthu.subjects.mapper.SubjectMapper;
import com.project.ptittoanthu.subjects.model.Subject;
import com.project.ptittoanthu.subjects.repository.SubjectRepository;
import com.project.ptittoanthu.users.exception.UserNotFoundException;
import com.project.ptittoanthu.users.model.Role;
import com.project.ptittoanthu.users.model.User;
import com.project.ptittoanthu.users.repository.UserRepository;
import com.project.ptittoanthu.users.service.TeacherManagementService;
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
public class TeacherManagementServiceImpl implements TeacherManagementService {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public PageResult<List<SubjectResponse>> getTheSubjectsOfTeacher(SearchRequest request) {
        Sort sort = SortHelper.buildSort(request.getOrder(), request.getDirection());
        Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize(), sort);

        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(""));
        Page<Subject> page = subjectRepository.findAllByTeacherRequest(user.getId(), request.getKeyword(), pageable);

        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(page, request);
        List<SubjectResponse> responses = page.getContent().stream()
                .map(subjectMapper::toSubjectResponse).toList();
        return PageResult.<List<SubjectResponse>>builder()
                .metaDataResponse(metaDataResponse)
                .data(responses)
                .build();
    }

    @Transactional
    @Override
    public void enrollToSubject(Integer subjectId) {
        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(""));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(""));
        user.getSubjects().add(subject);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void takeOutSubject(Integer subjectId) {
        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(""));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(""));
        user.getSubjects().remove(subject);
        userRepository.save(user);
    }
}
