package com.project.ptittoanthu.favorite.repository;

import com.project.ptittoanthu.favorite.model.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {

    boolean existsByUserEmailAndDocumentId(String email, Integer documentId);

    Optional<Bookmark> findByUserEmailAndDocumentId(String email, Integer documentId);

    @Query("""
            SELECT b FROM Bookmark b
            WHERE b.user.email = :email
              AND (
                    :keyword IS NULL
                    OR LOWER(b.document.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(b.document.author) LIKE LOWER(CONCAT('%', :keyword, '%'))
                  )
        """)
    Page<Bookmark> findAllBySearchAndUserEmail(
            @Param("keyword") String keyword,
            @Param("email") String email,
            Pageable pageable
    );
}
