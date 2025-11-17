package com.project.ptittoanthu.notify.dto;

import com.project.ptittoanthu.notify.model.NotificationType;
import com.project.ptittoanthu.users.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateNotificationRequest {
    private String title;
    private String message;
    private NotificationType type;
    private Integer targetId;
    private boolean read;
    private User user;
}
