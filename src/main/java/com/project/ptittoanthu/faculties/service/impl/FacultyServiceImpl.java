package com.project.ptittoanthu.faculties.service.impl;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.SearchRequest;
import com.project.ptittoanthu.common.helper.MetaDataHelper;
import com.project.ptittoanthu.common.helper.SortHelper;
import com.project.ptittoanthu.faculties.dto.CreateFacultyRequest;
import com.project.ptittoanthu.faculties.dto.FacultyResponse;
import com.project.ptittoanthu.faculties.dto.UpdateFacultyRequest;
import com.project.ptittoanthu.faculties.exception.FacultyCodeException;
import com.project.ptittoanthu.faculties.exception.FacultyNotFoundException;
import com.project.ptittoanthu.faculties.mapper.FacultyMapper;
import com.project.ptittoanthu.faculties.model.Faculty;
import com.project.ptittoanthu.faculties.repository.FacultyRepository;
import com.project.ptittoanthu.faculties.service.FacultyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    @Transactional
    @Override
    public FacultyResponse createFaculty(CreateFacultyRequest request) {
        if (facultyRepository.existsByCode(request.getCode()))
            throw new FacultyCodeException("Code existed");

        Faculty faculty = facultyMapper.toFaculty(request);
        facultyRepository.save(faculty);
        return facultyMapper.toFacultyResponse(faculty);
    }

    @Transactional
    @Override
    public FacultyResponse updateFaculty(UpdateFacultyRequest request) {
        Faculty faculty = facultyRepository.findById(request.getId())
                .orElseThrow(() -> new FacultyNotFoundException(""));

        if (facultyRepository.existsByCode(request.getCode()) && !faculty.getCode().equals(request.getCode()))
            throw new FacultyCodeException("Code existed");

        facultyMapper.updateFaculty(request, faculty);
        facultyRepository.save(faculty);
        return facultyMapper.toFacultyResponse(faculty);
    }

    @Override
    public PageResult<List<FacultyResponse>> getFaculties(SearchRequest searchRequest) {
        Sort sort = SortHelper.buildSort(searchRequest.getOrder(), searchRequest.getDirection());
        Pageable pageable = PageRequest.of(searchRequest.getCurrentPage() - 1, searchRequest.getPageSize(), sort);
        Page<Faculty> facultyPage = facultyRepository.findAllBySearchRequest(searchRequest.getKeyword(), pageable);
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(facultyPage, searchRequest);
        List<FacultyResponse> facultyResponses = facultyPage.getContent().stream().map(
                facultyMapper::toFacultyResponse
        ).toList();
        return PageResult.<List<FacultyResponse>>builder()
                .metaDataResponse(metaDataResponse)
                .data(facultyResponses)
                .build();
    }

    @Override
    public FacultyResponse getFaculty(Integer id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(""));
        return facultyMapper.toFacultyResponse(faculty);
    }

    @Transactional
    @Override
    public void deleteFaculty(Integer id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(""));
        faculty.setDeletedAt(OffsetDateTime.now());
        facultyRepository.save(faculty);
    }
}
