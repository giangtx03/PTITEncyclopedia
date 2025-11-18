package com.project.ptittoanthu.documents.repository;

import com.project.ptittoanthu.documents.dto.DocumentStatsDto;
import com.project.ptittoanthu.documents.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    @Override
    @Query("""
            SELECT d FROM Document d
            WHERE :id = d.id
            """)
    Optional<Document> findById(Integer id);
    @Query(value = """
        SELECT new com.project.ptittoanthu.documents.dto.DocumentStatsDto(
            d,
            COUNT(DISTINCT b.id),
            AVG(r.star)
        )
        FROM Document d
        LEFT JOIN d.subject subject
        LEFT JOIN d.bookmarks b
        LEFT JOIN d.reviews r
        WHERE :id = d.id
        """)
    Optional<DocumentStatsDto> findDocumentStatsById(Integer id);

    @Query(value = """
        SELECT new com.project.ptittoanthu.documents.dto.DocumentStatsDto(
            d,
            COUNT(DISTINCT b.id),
            AVG(r.star)
        )
        FROM Document d
        LEFT JOIN d.subject subject
        LEFT JOIN d.bookmarks b
        LEFT JOIN d.reviews r
        WHERE (:subjectId IS NULL OR :subjectId = subject.id)
            AND (:keyword IS NULL OR LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
        GROUP BY d.id
        """)
    Page<DocumentStatsDto> findAllWithStatsDto(@Param("keyword") String keyword,
                                               @Param("subjectId") Integer subjectId,
                                               Pageable pageable);
}
