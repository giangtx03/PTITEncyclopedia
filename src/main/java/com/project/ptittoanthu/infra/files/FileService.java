package com.project.ptittoanthu.infra.files;

import com.project.ptittoanthu.documents.model.DocumentType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String upload(MultipartFile file, DocumentType documentType) throws IOException;
    Resource download(String filename) throws IOException;
    void delete(String filename) throws IOException;
}
