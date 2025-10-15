package com.project.ptittoanthu.infra.files;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.InvalidPropertiesFormatException;
import java.util.UUID;

@Service("image-local")
@Primary
public class ImageLocalServiceImpl implements FileService {
    @Value("${image.path}")
    private String path;
    private Path rootLocation;

    @PostConstruct
    public void init() {
        try {
            rootLocation = Paths.get(path);
            if (Files.notExists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    @Override
    public String upload(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File rỗng");
        }

        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new InvalidPropertiesFormatException("Không phải file ảnh");
        }
        String contentType = file.getContentType().substring(6);
        String uniqueFilename = UUID.randomUUID().toString() + "." + contentType;

        Path uploadDir = Paths.get(path);
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);

        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;
    }

    @Override
    public Resource download(String filename) throws FileNotFoundException, MalformedURLException {
        Path fileSrc = Paths.get(path + "/" + filename);
        if (!Files.exists(fileSrc)) {
            throw new FileNotFoundException("Không tìm thấy file " + filename);
        }
        return new UrlResource(fileSrc.toUri());
    }

    @Override
    public void delete(String filename) throws IOException {
        Path fileSrc = Paths.get(path + "/" + filename);
        Files.deleteIfExists(fileSrc);
    }
}
