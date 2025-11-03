package com.project.ptittoanthu.documents.dto;

import com.project.ptittoanthu.common.base.dto.SearchRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentSearchRequest extends SearchRequest {
    private Integer subjectId;
}
