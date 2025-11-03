package com.project.ptittoanthu.documents.model;

public enum DocumentType {
    DOCS(0, "docx"), PDF(1, "pdf"), PPTX(2, "pptx");

    public final int value;
    public final String type;

    DocumentType(int value, String type) {
        this.value = value;
        this.type = type;
    }
}
