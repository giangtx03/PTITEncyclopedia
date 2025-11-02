package com.project.ptittoanthu.images;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface ImageService {
    Resource getImageFile(String fileName) throws IOException;
}
