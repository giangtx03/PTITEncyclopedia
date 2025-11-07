package com.project.ptittoanthu.quiz.repository;

import com.project.ptittoanthu.quiz.model.Quiz;
import com.project.ptittoanthu.quiz.model.QuizTime;
import com.project.ptittoanthu.quiz.model.QuizType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    @Override
    @Query("""
            SELECT q FROM Quiz q
            WHERE :id = q.id
            """)
    Optional<Quiz> findById(@Param("id") Integer id);

    @Query("""
            SELECT q FROM Quiz q
            LEFT JOIN q.subject s
            WHERE (:keyword IS NULL OR LOWER(q.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
              AND (:subjectId IS NULL OR s.id = :subjectId)
              AND (:type IS NULL OR q.type = :type)
              AND (:time IS NULL OR q.time = :time)
            """)
    Page<Quiz> findAllBySearchRequest(String keyword, Integer subjectId, QuizType type, QuizTime time, Pageable pageable);
}
