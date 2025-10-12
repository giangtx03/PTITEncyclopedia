package com.project.ptittoanthu.common.base.builder;

import com.project.ptittoanthu.common.base.dto.PageResponseDto;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.ResponseDto;
import lombok.experimental.UtilityClass;

import java.time.OffsetDateTime;

@UtilityClass
public class ResponseBuilder {
    public ResponseDto<Void> okResponse(int code, String message){
        return ResponseDto.<Void>builder()
                .success(true)
                .message(message)
                .statusCode(code)
                .time(OffsetDateTime.now())
                .build();
    }

    public <T> ResponseDto<T> okResponse(int code, String message, T data) {
        return ResponseDto.<T>builder()
                .success(true)
                .message(message)
                .statusCode(code)
                .data(data)
                .time(OffsetDateTime.now())
                .build();
    }

    public <T> PageResponseDto<T> okResponse(int code, String message, PageResult<T> result) {
        return PageResponseDto.<T>builder()
                .success(true)
                .message(message)
                .statusCode(code)
                .data(result.getData())
                .metaData(result.getMetaData())
                .time(OffsetDateTime.now())
                .build();
    }

    public ResponseDto<Void> errorResponse(int code, String message){
        return ResponseDto.<Void>builder()
                .success(false)
                .message(message)
                .statusCode(code)
                .time(OffsetDateTime.now())
                .build();
    }

    public <T> ResponseDto<T> errorResponse(int code, String message,T data) {
        return ResponseDto.<T>builder()
                .success(false)
                .message(message)
                .statusCode(code)
                .data(data)
                .time(OffsetDateTime.now())
                .build();
    }
}
