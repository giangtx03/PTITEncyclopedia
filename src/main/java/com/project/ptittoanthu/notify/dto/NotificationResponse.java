package com.project.ptittoanthu.notify.dto;

import com.project.ptittoanthu.common.base.dto.BaseResponse;
import com.project.ptittoanthu.notify.model.NotificationType;
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
public class NotificationResponse extends BaseResponse {
    private Integer id;
    private String title;
    private String message;
    private NotificationType type;
    private Integer targetId;
    private boolean read;
}
