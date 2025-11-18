package com.project.ptittoanthu.documents.dto;

import com.project.ptittoanthu.documents.model.Document;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentStatsDto {
    private Document document;
    private Long bookmarkCount;
    private Double avgRating;

    public DocumentStatsDto(Document document, Long bookmarkCount, Double avgRating) {
        this.document = document;
        this.bookmarkCount = bookmarkCount != null ? bookmarkCount : 0L;
        this.avgRating = avgRating != null ? (Math.round(avgRating * 10.0) / 10.0) : 0.0;
    }
}
