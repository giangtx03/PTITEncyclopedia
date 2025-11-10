package com.project.ptittoanthu.subjects.service.impl;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.helper.MetaDataHelper;
import com.project.ptittoanthu.common.helper.SortHelper;
import com.project.ptittoanthu.common.util.SecurityUtils;
import com.project.ptittoanthu.faculties.exception.FacultyNotFoundException;
import com.project.ptittoanthu.majors.exception.MajorCodeException;
import com.project.ptittoanthu.majors.exception.MajorNotFoundException;
import com.project.ptittoanthu.majors.model.Major;
import com.project.ptittoanthu.majors.repository.MajorRepository;
import com.project.ptittoanthu.subjects.dto.request.CreateSubjectRequest;
import com.project.ptittoanthu.subjects.dto.request.SubjectSearchRequest;
import com.project.ptittoanthu.subjects.dto.request.UpdateSubjectRequest;
import com.project.ptittoanthu.subjects.dto.response.SubjectResponse;
import com.project.ptittoanthu.subjects.dto.response.SubjectResponseDetail;
import com.project.ptittoanthu.subjects.exception.SubjectCodeException;
import com.project.ptittoanthu.subjects.exception.SubjectNotFoundException;
import com.project.ptittoanthu.subjects.mapper.SubjectMapper;
import com.project.ptittoanthu.subjects.model.Subject;
import com.project.ptittoanthu.subjects.repository.SubjectRepository;
import com.project.ptittoanthu.subjects.service.SubjectService;
import com.project.ptittoanthu.users.exception.UserNotFoundException;
import com.project.ptittoanthu.users.model.User;
import com.project.ptittoanthu.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final UserRepository userRepository;
    private final MajorRepository majorRepository;

    @Transactional
    @Override
    public SubjectResponseDetail createSubject(CreateSubjectRequest request) {
        if (subjectRepository.existsByCode(request.getCode()))
            throw new SubjectCodeException("Code existed");

        Major major = majorRepository.findById(request.getMajorId())
                .orElseThrow(() -> new MajorNotFoundException("Major not found"));

        Subject subject = subjectMapper.toSubject(request);
        subject.setMajor(major);
        subjectRepository.save(subject);
        return subjectMapper.toSubjectDetailResponse(subject);
    }

    @Transactional
    @Override
    public SubjectResponseDetail updateSubject(UpdateSubjectRequest request) {
        Subject subject = subjectRepository.findById(request.getId())
                .orElseThrow(() -> new SubjectNotFoundException(""));

        if (subjectRepository.existsByCode(request.getCode()) && !subject.getCode().equals(request.getCode()))
            throw new MajorCodeException("Code existed");

        Major major = majorRepository.findById(request.getMajorId())
                .orElseThrow(() -> new FacultyNotFoundException(""));

        subjectMapper.updateSubject(request, subject);
        subject.setMajor(major);
        subjectRepository.save(subject);
        return subjectMapper.toSubjectDetailResponse(subject);
    }

    @Override
    public PageResult<List<SubjectResponse>> getSubjects(SubjectSearchRequest searchRequest) {
        Sort sort = SortHelper.buildSort(searchRequest.getOrder(), searchRequest.getDirection());
        Pageable pageable = PageRequest.of(searchRequest.getCurrentPage() - 1, searchRequest.getPageSize(), sort);
        Page<Subject> subjectPage = subjectRepository.findAllBySearchRequest(searchRequest.getMajorId(),
                searchRequest.getKeyword(), pageable);
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(subjectPage, searchRequest);
        List<SubjectResponse> subjectResponses = subjectPage.getContent().stream().map(
                subjectMapper::toSubjectResponse
        ).toList();
        return PageResult.<List<SubjectResponse>>builder()
                .metaDataResponse(metaDataResponse)
                .data(subjectResponses)
                .build();
    }

    @Transactional
    @Override
    public void joinSubject(Integer subjectId) {

        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(""));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found"));

        subject.getUsers().add(user);
        subjectRepository.save(subject);
    }

    @Transactional
    @Override
    public void leaveSubject(Integer subjectId) {
        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(""));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException("Subject not found"));

        subject.getUsers().remove(user);
        subjectRepository.save(subject);
    }

    @Override
    public SubjectResponseDetail getSubject(Integer id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(""));
        return subjectMapper.toSubjectDetailResponse(subject);
    }

    @Transactional
    @Override
    public void deleteSubject(Integer id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(""));
        subject.setDeletedAt(OffsetDateTime.now());
        subjectRepository.save(subject);
    }
}
