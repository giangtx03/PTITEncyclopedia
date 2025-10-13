package com.project.ptittoanthu.images;

import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public interface ImageService {
    Resource getImageFile(String fileName) throws MalformedURLException, FileNotFoundException;
}
