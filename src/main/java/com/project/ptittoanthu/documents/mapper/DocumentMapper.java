package com.project.ptittoanthu.documents.mapper;

import com.project.ptittoanthu.documents.dto.request.CreateDocumentRequest;
import com.project.ptittoanthu.documents.dto.request.UpdateDocumentRequest;
import com.project.ptittoanthu.documents.dto.response.DocumentResponse;
import com.project.ptittoanthu.documents.dto.response.DocumentResponseDetail;
import com.project.ptittoanthu.documents.model.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "filePath", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "subject", ignore = true)
    Document toDocument(CreateDocumentRequest request);

    List<DocumentResponse> toDocumentResponse(List<Document> documents);
    DocumentResponse toDocumentResponse(Document document);
    DocumentResponseDetail toDocumentResponseDetail(Document document);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "filePath", ignore = true)
    void updateDoc(UpdateDocumentRequest request,@MappingTarget Document document);
}
