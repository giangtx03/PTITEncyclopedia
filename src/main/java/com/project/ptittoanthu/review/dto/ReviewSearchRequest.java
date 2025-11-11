package com.project.ptittoanthu.review.dto;

import com.project.ptittoanthu.common.base.dto.SearchRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReviewSearchRequest extends SearchRequest {
    private Integer documentId;
    private int star;
}
