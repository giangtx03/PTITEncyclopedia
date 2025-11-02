package com.project.ptittoanthu.infra.files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

@Service("image-local")
@Primary
public class ImageLocalServiceImpl extends AbstractFileService {
    @Value("${image.path}")
    private String path;

    @Override
    protected String getPath() {
        return path;
    }

    @Override
    protected void validateFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File rỗng");
        }

        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new InvalidPropertiesFormatException("Không phải file ảnh");
        }
    }
}
