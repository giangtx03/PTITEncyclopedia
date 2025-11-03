package com.project.ptittoanthu.files;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface FileDownloadService {
    Resource getResourceFile(String fileName, FileType fileType) throws IOException;
}
