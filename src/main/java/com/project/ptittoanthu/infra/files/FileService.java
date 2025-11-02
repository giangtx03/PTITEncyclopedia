package com.project.ptittoanthu.infra.files;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String upload(MultipartFile file) throws IOException;
    Resource download(String filename) throws IOException;
    void delete(String filename) throws IOException;
}
