package com.project.ptittoanthu.infra.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service("gmail")
@Primary
@RequiredArgsConstructor
@Slf4j
public class EmailSenderImpl implements MailSender {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Async("emailExecutor")
    @Override
    public CompletableFuture<String> sendEmailAsync(String to, String subject, String template, Map<String, Object> variables)
            throws MessagingException {
        Context context = new Context();
        context.setVariables(variables);

        String htmlContent = templateEngine.process(template, context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        mailSender.send(message);

        log.error(htmlContent);
        return CompletableFuture.completedFuture("Email sent successfully to " + to);
    }
}
