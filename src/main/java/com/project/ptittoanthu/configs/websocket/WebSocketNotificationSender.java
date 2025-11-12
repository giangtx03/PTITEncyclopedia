package com.project.ptittoanthu.configs.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketNotificationSender {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendToUser(Integer userId, Object payload) {
        messagingTemplate.convertAndSend("/topic/notifications/" + userId, payload);
    }
}

