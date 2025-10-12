package com.project.ptittoanthu.infra.images;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public interface FileService {
    String upload(MultipartFile file) throws IOException;
    Resource download(String filename) throws FileNotFoundException, MalformedURLException;
    void delete(String path) throws IOException;
}
