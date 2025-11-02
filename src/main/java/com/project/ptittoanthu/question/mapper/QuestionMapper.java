package com.project.ptittoanthu.question.mapper;

import com.project.ptittoanthu.question.dto.request.CreateOptionRequest;
import com.project.ptittoanthu.question.dto.request.CreateQuestionRequest;
import com.project.ptittoanthu.question.dto.request.CreateTipRequest;
import com.project.ptittoanthu.question.dto.request.UpdateQuestionRequest;
import com.project.ptittoanthu.question.dto.response.QuestionResponse;
import com.project.ptittoanthu.question.dto.response.QuestionResponseDetail;
import com.project.ptittoanthu.question.model.Option;
import com.project.ptittoanthu.question.model.Question;
import com.project.ptittoanthu.question.model.Tip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TipMapper.class, OptionMapper.class})
public interface QuestionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "options", ignore = true)
    @Mapping(target = "tips", ignore = true)
    Question toEntity(CreateQuestionRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "options", ignore = true)
    @Mapping(target = "tips", ignore = true)
    void updateEntity(UpdateQuestionRequest request, @MappingTarget Question question);

    List<QuestionResponse> toQuestionResponse(List<Question> questions);
    QuestionResponseDetail toQuestionResponseDetail(Question question);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    Tip toTip(CreateTipRequest request);
    List<Tip> toTip(List<CreateTipRequest> requests);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    Option toOption(CreateOptionRequest options);
    List<Option> toOption(List<CreateOptionRequest> options);
}
