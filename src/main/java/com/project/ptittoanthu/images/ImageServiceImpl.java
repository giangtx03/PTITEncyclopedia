package com.project.ptittoanthu.images;

import com.project.ptittoanthu.infra.files.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final FileService fileService;

    @Override
    public Resource getImageFile(String fileName)
            throws IOException {
        return fileService.download(fileName);
    }
}
