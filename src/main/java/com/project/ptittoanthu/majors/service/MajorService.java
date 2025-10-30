package com.project.ptittoanthu.majors.service;

import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.majors.dto.CreateMajorRequest;
import com.project.ptittoanthu.majors.dto.MajorResponse;
import com.project.ptittoanthu.majors.dto.MajorResponseDetail;
import com.project.ptittoanthu.majors.dto.MajorSearchRequest;
import com.project.ptittoanthu.majors.dto.UpdateMajorRequest;

import java.util.List;

public interface MajorService {
    MajorResponseDetail createMajor(CreateMajorRequest request);
    MajorResponseDetail updateMajor(UpdateMajorRequest request);
    PageResult<List<MajorResponse>> getMajors(MajorSearchRequest searchRequest);
    MajorResponseDetail getMajor(Integer id);
    void deleteMajor(Integer id);
}
