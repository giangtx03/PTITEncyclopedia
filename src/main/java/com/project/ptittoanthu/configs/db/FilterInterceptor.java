package com.project.ptittoanthu.configs.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class FilterInterceptor {
    @PersistenceContext
    private EntityManager entityManager;

    private static final ThreadLocal<Boolean> DISABLE_FILTER = ThreadLocal.withInitial(() -> false);

    public void enableFilter() {
        if (!DISABLE_FILTER.get()) {
            Session session = entityManager.unwrap(Session.class);
            session.enableFilter("softDeleteFilter");
        }
    }

    public void disableFilterForCurrentThread() {
        DISABLE_FILTER.set(true);
    }

    public void enableFilterForCurrentThread() {
        DISABLE_FILTER.set(false);
    }

    public void clearThreadLocal() {
        DISABLE_FILTER.remove();
    }
}
