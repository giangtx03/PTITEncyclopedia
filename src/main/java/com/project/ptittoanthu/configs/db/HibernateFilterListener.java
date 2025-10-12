package com.project.ptittoanthu.configs.db;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class HibernateFilterListener {
    private final FilterInterceptor filterInterceptor;

    @Before("execution(* com.project.ptittoanthu..service.impl..*(..))")
    public void applyFilter() {
        filterInterceptor.enableFilter();
    }
}
