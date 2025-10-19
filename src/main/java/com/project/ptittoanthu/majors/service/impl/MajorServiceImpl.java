package com.project.ptittoanthu.majors.service.impl;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.SearchRequest;
import com.project.ptittoanthu.common.helper.MetaDataHelper;
import com.project.ptittoanthu.common.helper.SortHelper;
import com.project.ptittoanthu.faculties.dto.FacultyResponse;
import com.project.ptittoanthu.faculties.exception.FacultyNotFoundException;
import com.project.ptittoanthu.faculties.model.Faculty;
import com.project.ptittoanthu.faculties.repository.FacultyRepository;
import com.project.ptittoanthu.majors.dto.CreateMajorRequest;
import com.project.ptittoanthu.majors.dto.MajorResponse;
import com.project.ptittoanthu.majors.dto.MajorSearchRequest;
import com.project.ptittoanthu.majors.dto.UpdateMajorRequest;
import com.project.ptittoanthu.majors.exception.MajorCodeException;
import com.project.ptittoanthu.majors.exception.MajorNotFoundException;
import com.project.ptittoanthu.majors.mapper.MajorMapper;
import com.project.ptittoanthu.majors.model.Major;
import com.project.ptittoanthu.majors.repository.MajorRepository;
import com.project.ptittoanthu.majors.service.MajorService;
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
public class MajorServiceImpl implements MajorService {

    private final MajorRepository majorRepository;
    private final FacultyRepository facultyRepository;
    private final MajorMapper majorMapper;

    @Transactional
    @Override
    public MajorResponse createMajor(CreateMajorRequest request) {
        if (majorRepository.existsByCode(request.getCode()))
            throw new MajorCodeException("Code existed");

        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() -> new FacultyNotFoundException(""));

        Major major = majorMapper.toMajor(request);
        major.setFaculty(faculty);
        majorRepository.save(major);
        return majorMapper.toMajorResponse(major);
    }

    @Transactional
    @Override
    public MajorResponse updateMajor(UpdateMajorRequest request) {
        Major major = majorRepository.findById(request.getId())
                .orElseThrow(() -> new MajorNotFoundException(""));

        if (majorRepository.existsByCode(request.getCode()) && !major.getCode().equals(request.getCode()))
            throw new MajorCodeException("Code existed");

        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() -> new FacultyNotFoundException(""));

        majorMapper.updateMajor(request, major);
        major.setFaculty(faculty);
        majorRepository.save(major);
        return majorMapper.toMajorResponse(major);
    }

    @Override
    public PageResult<List<MajorResponse>> getMajors(MajorSearchRequest searchRequest) {
        Sort sort = SortHelper.buildSort(searchRequest.getOrder(), searchRequest.getDirection());
        Pageable pageable = PageRequest.of(searchRequest.getCurrentPage() - 1, searchRequest.getPageSize(), sort);
        Page<Major> majorPage = majorRepository.findAllBySearchRequest(searchRequest.getFacultyId() ,searchRequest.getKeyword(), pageable);
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(majorPage, searchRequest);
        List<MajorResponse> facultyResponses = majorPage.getContent().stream().map(
                majorMapper::toMajorResponse
        ).toList();
        return PageResult.<List<MajorResponse>>builder()
                .metaDataResponse(metaDataResponse)
                .data(facultyResponses)
                .build();
    }

    @Override
    public MajorResponse getMajor(Integer id) {
        Major major = majorRepository.findById(id)
                .orElseThrow(() -> new MajorNotFoundException(""));
        return majorMapper.toMajorResponse(major);
    }

    @Override
    public void deleteMajor(Integer id) {
        Major major = majorRepository.findById(id)
                .orElseThrow(() -> new MajorNotFoundException(""));

        major.setDeletedAt(OffsetDateTime.now());
        majorRepository.save(major);
    }
}
