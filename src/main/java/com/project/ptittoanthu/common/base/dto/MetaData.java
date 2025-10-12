package com.project.ptittoanthu.common.base.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {
    private int currentPage;
    private int pageSize;
    private int totalPage;
    private long totalItems;
}
