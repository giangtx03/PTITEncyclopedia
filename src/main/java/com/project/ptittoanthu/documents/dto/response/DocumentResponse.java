package com.project.ptittoanthu.documents.dto.response;

import com.project.ptittoanthu.common.base.constant.PropertyUrlConstant;
import com.project.ptittoanthu.common.base.dto.BaseResponse;
import com.project.ptittoanthu.configs.aop.UrlPrefix;
import com.project.ptittoanthu.documents.model.DocumentType;
import com.project.ptittoanthu.users.dto.response.UserResponse;
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
public class DocumentResponse extends BaseResponse {
    Integer id;
    String title;
    DocumentType type;
    @UrlPrefix(property = PropertyUrlConstant.FILE_URL)
    String filePath;
    UserResponse owner;
}
