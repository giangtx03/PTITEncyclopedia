package com.project.ptittoanthu.quiz.repository;

import com.project.ptittoanthu.quiz.model.QuizResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuizResultRepository extends JpaRepository<QuizResult, Integer> {

    @Override
    @Query("""
            SELECT qr FROM QuizResult qr
            WHERE qr.id = :id
            """)
    Optional<QuizResult> findById(@Param("id") Integer id);

    @Query("""
        SELECT qr FROM QuizResult qr
        JOIN qr.quiz q
        JOIN q.subject s
        JOIN qr.user u
        WHERE (:keyword IS NULL OR LOWER(q.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')))
          AND (:subjectId IS NULL OR s.id = :subjectId)
          AND (:userId IS NULL OR u.id = :userId)
    """)
    Page<QuizResult> findAllBySearch(
            @Param("keyword") String keyword,
            @Param("subjectId") Integer subjectId,
            @Param("userId") Integer userId,
            Pageable pageable
    );
}
