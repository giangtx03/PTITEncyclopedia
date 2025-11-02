package com.project.ptittoanthu.users.dto.response;

import com.project.ptittoanthu.common.base.constant.PropertyUrlConstant;
import com.project.ptittoanthu.common.base.dto.BaseResponse;
import com.project.ptittoanthu.configs.aop.UrlPrefix;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserResponse extends BaseResponse {
    private Integer id;
    private String username;
    @UrlPrefix(property = PropertyUrlConstant.IMAGE_URL)
    private String avatar;
}
