package com.project.ptittoanthu.notify.service;

import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.PaginationRequest;
import com.project.ptittoanthu.notify.dto.CreateNotificationRequest;
import com.project.ptittoanthu.notify.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {
    void createNotification(CreateNotificationRequest request);
    PageResult<List<NotificationResponse>> getNotifications(PaginationRequest request);
    void readAll();
    void delete(Integer id);
}
