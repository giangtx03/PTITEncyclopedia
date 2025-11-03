package com.project.ptittoanthu.files;

import com.project.ptittoanthu.infra.files.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileDownloadServiceImpl implements FileDownloadService {

    private final Map<String, FileService> fileServices;

    @Override
    public Resource getResourceFile(String fileName, FileType fileType)
            throws IOException {
        FileService fileService = fileServices.get(fileType.getBeanName());
        if (fileService == null) throw new IllegalArgumentException("No file service found for " + fileType);
        return fileService.download(fileName);
    }
}
