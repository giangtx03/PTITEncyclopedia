package com.project.ptittoanthu.common.helper;

import org.springframework.data.domain.Sort;

public class SortHelper {
    public static Sort buildSort(String order, String direction) {
        if (order == null || order.isBlank()) return Sort.unsorted();

        Sort.Direction dir = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(direction)) {
            dir = Sort.Direction.DESC;
        }

        return Sort.by(dir, order);
    }
}
