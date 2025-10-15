package com.project.ptittoanthu.infra.files;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.InvalidPropertiesFormatException;
import java.util.UUID;

@Service("file-local")
public class FileServiceImpl implements FileService {

    @Value("${file.path}")
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

        if (file.getContentType() == null) {
            throw new InvalidPropertiesFormatException("File không hỗ trợ");
        }
        String contentType = file.getContentType().substring(6);
        String uniqueFilename = UUID.randomUUID().toString() + "." + contentType;

        Path destinationFile = this.rootLocation.resolve(Paths.get(uniqueFilename))
                .normalize().toAbsolutePath();

        // Đảm bảo đường dẫn đích nằm trong thư mục lưu trữ
        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            throw new IOException("Cannot store file outside current directory.");
        }

        // Copy nội dung file vào đường dẫn đích
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }

        return uniqueFilename;
    }

    @Override
    public Resource download(String filename) throws FileNotFoundException, MalformedURLException {
        Path fileSrc = Paths.get(path + "/" + filename);
        Resource resource = new UrlResource(fileSrc.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            throw new FileNotFoundException("Không thể đọc file: " + filename);
        }
        return resource;
    }

    @Override
    public void delete(String filename) throws IOException {
        Path fileSrc = Paths.get(path + "/" + filename);
        Files.deleteIfExists(fileSrc);
    }
}
