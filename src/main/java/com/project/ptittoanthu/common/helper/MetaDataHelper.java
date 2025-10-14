package com.project.ptittoanthu.common.helper;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PaginationRequest;
import org.springframework.data.domain.Page;

public class MetaDataHelper {
    public static <T> MetaDataResponse buildMetaData(Page<T> page, PaginationRequest request) {
        if (page == null || request == null) {
            throw new IllegalArgumentException("Page or request must not be null");
        }
        return MetaDataResponse.builder()
                .currentPage(request.getCurrentPage())
                .pageSize(request.getPageSize())
                .totalItems(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .build();
    }
}
