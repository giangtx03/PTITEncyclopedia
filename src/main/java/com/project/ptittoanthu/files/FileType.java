package com.project.ptittoanthu.files;

public enum FileType {
    IMAGE("image-local"),
    DOCUMENT("file-local");

    private final String beanName;

    FileType(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
