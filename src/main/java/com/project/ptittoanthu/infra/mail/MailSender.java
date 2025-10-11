package com.project.ptittoanthu.infra.mail;

import jakarta.mail.MessagingException;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface MailSender {
    CompletableFuture<String> sendEmailAsync(
            String to, String subject, String template, Map<String, Object> variables
    ) throws MessagingException;
}
