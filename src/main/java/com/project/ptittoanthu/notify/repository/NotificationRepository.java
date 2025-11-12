package com.project.ptittoanthu.notify.repository;

import com.project.ptittoanthu.notify.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query("""
            SELECT n FROM Notification n
            WHERE n.user.email = :userEmail
            ORDER BY n.createdAt DESC
        """)
    Page<Notification> findAllByUserEmail(@Param("userEmail") String userEmail, Pageable pageable);

    @Query("""
            SELECT n FROM Notification n
            WHERE n.user.email = :userEmail
            AND n.read = :read
        """)
    List<Notification> findAllByUserEmailAndRead(@Param("userEmail") String userEmail,@Param("read") boolean read);
}
