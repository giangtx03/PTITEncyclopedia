package com.project.ptittoanthu.documents.model;

public enum DocumentType {
    DOCS(0), PDF(1), PPTX(2);

    public final int value;

    DocumentType(int value) {
        this.value = value;
    }
}
