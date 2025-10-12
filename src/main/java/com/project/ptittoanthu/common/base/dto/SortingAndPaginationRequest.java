package com.project.ptittoanthu.common.base.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SortingAndPaginationRequest extends PaginationRequest {
    @Parameter(example = "updatedAt")
    private String order;
    @Parameter(example = "desc")
    private String direction;

    public SortingAndPaginationRequest() {
        super();
        this.order = "updatedAt";
        this.direction = "desc";
    }
}
