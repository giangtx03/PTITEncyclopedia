package com.project.ptittoanthu.review.repository;

import com.project.ptittoanthu.review.model.Review;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Override
    @Query("""
            SELECT r FROM Review r
            WHERE r.id = :id
            """)
    Optional<Review> findById(@Param("id") Integer id);

    @Query("""
            SELECT r FROM Review r
            WHERE (:keyword IS NULL OR LOWER(r.content) LIKE LOWER(CONCAT('%', :keyword, '%')))
              AND (:star = 0 OR r.star = :star)
              AND (:documentId IS NULL OR r.document.id = :documentId)
            """)
    Page<Review> findAllBySearch(String keyword, int star, Integer documentId, Pageable pageable);
    @Query("""
            SELECT r FROM Review r
            WHERE r.id = :id AND r.user.email = :userEmail
            """)
    Optional<Review> findByIdWithUserEmail(@Param("id")@NotNull Integer id,@Param("userEmail") String userEmail);
}
