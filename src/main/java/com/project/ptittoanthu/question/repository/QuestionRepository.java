package com.project.ptittoanthu.question.repository;

import com.project.ptittoanthu.question.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Override
    @Query("""
            SELECT q FROM Question q
            WHERE q.id = :id
            """)
    Optional<Question> findById(@Param("id") Integer id);

    @EntityGraph(attributePaths = {"options"})
    @Query("""
            SELECT q FROM Question q
            LEFT JOIN q.quizzes quiz
            WHERE (:quizId IS NULL OR quiz.id = :quizId)
            AND (:keyword IS NULL OR LOWER(q.content) LIKE LOWER(CONCAT('%', :keyword, '%')))
            """)
    Page<Question> findAllBySearchRequest(@Param("keyword") String keyword,
                                          @Param("quizId") Integer quizId, Pageable pageable);

    @Query("""
            SELECT q FROM Question q
            WHERE q.subject.id = :subjectId
            ORDER BY function('RAND')
        """)
    List<Question> findRandomQuestionsBySubject(@Param("subjectId") Integer subjectId, Pageable pageable);

}
