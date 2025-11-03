package com.project.ptittoanthu.documents.service;

import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.documents.dto.DocumentSearchRequest;
import com.project.ptittoanthu.documents.dto.request.CreateDocumentRequest;
import com.project.ptittoanthu.documents.dto.request.UpdateDocumentRequest;
import com.project.ptittoanthu.documents.dto.response.DocumentResponse;
import com.project.ptittoanthu.documents.dto.response.DocumentResponseDetail;

import java.io.IOException;
import java.util.List;

public interface DocumentService {
    DocumentResponseDetail insertDocument(CreateDocumentRequest request) throws IOException;
    DocumentResponseDetail updateDocument(UpdateDocumentRequest request) throws IOException;
    DocumentResponseDetail getDocument(Integer id);
    DocumentResponseDetail approveDocument(Integer id);
    DocumentResponseDetail disableDocument(Integer id);
    void deleteDocument(Integer id);
    PageResult<List<DocumentResponse>> getDocs(DocumentSearchRequest request);
}
