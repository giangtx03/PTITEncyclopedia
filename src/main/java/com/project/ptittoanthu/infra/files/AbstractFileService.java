package com.project.ptittoanthu.infra.files;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

public abstract class AbstractFileService implements FileService {
    protected Path rootLocation;

    protected abstract String getPath();

    protected abstract void validateFile(MultipartFile file) throws IOException;

    @PostConstruct
    public void init() {
        try {
            rootLocation = Paths.get(getPath());
            if (Files.notExists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    @Override
    public String upload(MultipartFile file) throws IOException {
        validateFile(file);

        String contentType = Objects.requireNonNull(file.getContentType()).substring(6);
        String uniqueFilename = UUID.randomUUID() + "." + contentType;

        Path destination = rootLocation.resolve(uniqueFilename).normalize().toAbsolutePath();
        if (!destination.getParent().equals(rootLocation.toAbsolutePath())) {
            throw new IOException("Cannot store file outside current directory");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
        }

        return uniqueFilename;
    }

    @Override
    public Resource download(String filename) throws IOException {
        Path fileSrc = rootLocation.resolve(filename);
        Resource resource = new UrlResource(fileSrc.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            throw new FileNotFoundException("Không thể đọc file: " + filename);
        }
        return resource;
    }

    @Override
    public void delete(String filename) throws IOException {
        Path fileSrc = rootLocation.resolve(filename);
        Files.deleteIfExists(fileSrc);
    }
}
