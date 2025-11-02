package com.project.ptittoanthu.question.mapper;

import com.project.ptittoanthu.question.dto.request.UpdateOptionRequest;
import com.project.ptittoanthu.question.dto.response.OptionResponse;
import com.project.ptittoanthu.question.dto.response.OptionResponseDetail;
import com.project.ptittoanthu.question.model.Option;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OptionMapper {
    @Mapping(target = "id", ignore = true)
    void updateOption(UpdateOptionRequest request, @MappingTarget Option option);

    List<OptionResponse> toOptionResponse(List<Option> options);
    List<OptionResponseDetail> toOptionsResponseDetail(List<Option> options);
    OptionResponse toOptionResponse(Option option);
    OptionResponseDetail toOptionResponseDetail(Option option);
}
