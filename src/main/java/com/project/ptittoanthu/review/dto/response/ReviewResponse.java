package com.project.ptittoanthu.review.dto.response;

import com.project.ptittoanthu.common.base.dto.BaseResponse;
import com.project.ptittoanthu.users.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class ReviewResponse extends BaseResponse {
    private Integer id;
    private String content;
    private int star;
    private UserResponse user;
}
