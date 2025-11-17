package com.project.ptittoanthu.notify.mapper;

import com.project.ptittoanthu.notify.dto.CreateNotificationRequest;
import com.project.ptittoanthu.notify.dto.NotificationResponse;
import com.project.ptittoanthu.notify.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "id", ignore = true)
    Notification toNotification(CreateNotificationRequest request);

    NotificationResponse toNotificationResponse(Notification notification);
    List<NotificationResponse> toNotificationResponse(List<Notification> notifications);
}
