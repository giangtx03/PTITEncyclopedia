package com.project.ptittoanthu.notify.service.impl;

import com.project.ptittoanthu.common.base.dto.MetaDataResponse;
import com.project.ptittoanthu.common.base.dto.PageResult;
import com.project.ptittoanthu.common.base.dto.PaginationRequest;
import com.project.ptittoanthu.common.helper.MetaDataHelper;
import com.project.ptittoanthu.common.util.SecurityUtils;
import com.project.ptittoanthu.configs.websocket.WebSocketNotificationSender;
import com.project.ptittoanthu.notify.dto.CreateNotificationRequest;
import com.project.ptittoanthu.notify.dto.NotificationResponse;
import com.project.ptittoanthu.notify.mapper.NotificationMapper;
import com.project.ptittoanthu.notify.model.Notification;
import com.project.ptittoanthu.notify.repository.NotificationRepository;
import com.project.ptittoanthu.notify.service.NotificationService;
import com.project.ptittoanthu.users.exception.UserNotFoundException;
import com.project.ptittoanthu.users.model.User;
import com.project.ptittoanthu.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;
    private final WebSocketNotificationSender webSocketNotificationSender;

    @Override
    public NotificationResponse createNotification(CreateNotificationRequest request) {
        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(""));

        Notification notification = notificationMapper.toNotification(request);
        notification.setUser(user);
        notificationRepository.save(notification);
        NotificationResponse response = notificationMapper.toNotificationResponse(notification);
        webSocketNotificationSender.sendToUser(user.getId(), response);
        return response;
    }

    @Override
    public PageResult<List<NotificationResponse>> getNotifications(PaginationRequest request) {
        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize());

        Page<Notification> notifications = notificationRepository.findAllByUserEmail(userEmail, pageable);
        List<NotificationResponse> responses = notificationMapper.toNotificationResponse(notifications.getContent());
        MetaDataResponse metaDataResponse = MetaDataHelper.buildMetaData(notifications, request);
        return  PageResult.<List<NotificationResponse>>builder()
                .metaDataResponse(metaDataResponse)
                .data(responses)
                .build();
    }

    @Override
    public void readAll() {
        String userEmail = SecurityUtils.getUserEmailFromSecurity();
        List<Notification> notifications = notificationRepository.findAllByUserEmailAndRead(userEmail, false);
        for(Notification notification : notifications) notification.setRead(true);
        notificationRepository.saveAll(notifications);
    }

    @Override
    public void delete(Integer id) {
        notificationRepository.deleteById(id);
    }
}
