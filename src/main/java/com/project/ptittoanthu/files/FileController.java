package com.project.ptittoanthu.files;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
public class FileController {

    private final FileDownloadService fileDownloadService;

    @GetMapping("/images/{fileName}")
    public ResponseEntity<?> getImage(@PathVariable("fileName") String fileName)
            throws IOException {
        Resource source = fileDownloadService.getResourceFile(fileName, FileType.IMAGE);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(source);
    }

    @GetMapping("/files/{fileName}")
    public ResponseEntity<?> getFile(@PathVariable("fileName") String fileName)
            throws IOException {
        Resource source = fileDownloadService.getResourceFile(fileName, FileType.DOCUMENT);
        Path path = source.getFile().toPath();
        String contentType = Files.probeContentType(path);

        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + source.getFilename() + "\"")
                .body(source);
    }
}
