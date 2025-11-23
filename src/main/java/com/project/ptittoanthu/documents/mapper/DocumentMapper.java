package com.project.ptittoanthu.documents.mapper;

import com.project.ptittoanthu.documents.dto.DocumentStatsDto;
import com.project.ptittoanthu.documents.dto.request.CreateDocumentRequest;
import com.project.ptittoanthu.documents.dto.request.UpdateDocumentRequest;
import com.project.ptittoanthu.documents.dto.response.DocumentResponse;
import com.project.ptittoanthu.documents.dto.response.DocumentResponseDetail;
import com.project.ptittoanthu.documents.model.Document;
import com.project.ptittoanthu.subjects.mapper.SubjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SubjectMapper.class})
public interface DocumentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "filePath", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "subject", ignore = true)
    Document toDocument(CreateDocumentRequest request);

    DocumentResponse toDocumentResponse(Document document);
    DocumentResponseDetail toDocumentResponseDetail(Document document);
    default DocumentResponseDetail toDocumentResponseDetail(DocumentStatsDto dto) {
        if (dto == null) {
            return null;
        }
        DocumentResponseDetail response = toDocumentResponseDetail(dto.getDocument());

        if (response != null) {
            response.setBookmarkCount(dto.getBookmarkCount());
            response.setAvgRating(dto.getAvgRating());
        }

        return response;
    }

    List<DocumentResponse> toDocumentResponse(List<DocumentStatsDto> dtos);

    default DocumentResponse toDocumentResponse(DocumentStatsDto dto) {
        if (dto == null) {
            return null;
        }
        DocumentResponse response = toDocumentResponse(dto.getDocument());

        if (response != null) {
            response.setBookmarkCount(dto.getBookmarkCount());
            response.setAvgRating(dto.getAvgRating());
        }

        return response;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "filePath", ignore = true)
    void updateDoc(UpdateDocumentRequest request,@MappingTarget Document document);
}
