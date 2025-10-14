package com.project.ptittoanthu.infra.language;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class LanguageService {
    private final MessageSource messageSource;

    public String getMessage(String messageCode) {
        try {
            return messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return "Message not found for code: " + messageCode;
        }
    }

    public String getMessage(String messageCode, Object ...args){
        if (args == null || Arrays.stream(args).allMatch(Objects::isNull)) {
            args = new Object[]{""};
        }
        log.info(messageCode + " " + LocaleContextHolder.getLocale());
        return messageSource.getMessage(messageCode, args, LocaleContextHolder.getLocale());
    }
}
