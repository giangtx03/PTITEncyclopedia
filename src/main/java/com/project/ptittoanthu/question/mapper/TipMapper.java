package com.project.ptittoanthu.question.mapper;

import com.project.ptittoanthu.question.dto.request.UpdateTipRequest;
import com.project.ptittoanthu.question.dto.response.TipResponse;
import com.project.ptittoanthu.question.model.Tip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TipMapper {
    TipResponse toTipResponse(Tip tips);
    List<TipResponse> toTipResponses(List<Tip> tips);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    void updateTip(UpdateTipRequest request, @MappingTarget Tip tip);
}
