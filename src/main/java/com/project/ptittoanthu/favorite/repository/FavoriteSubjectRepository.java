package com.project.ptittoanthu.favorite.repository;

import com.project.ptittoanthu.favorite.model.FavoriteSubject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FavoriteSubjectRepository extends JpaRepository<FavoriteSubject, Integer> {

    boolean existsByUserEmailAndSubjectId(String email, Integer subjectId);

    Optional<FavoriteSubject> findByUserEmailAndSubjectId(String email, Integer subjectId);

    @Query("""
                SELECT f FROM FavoriteSubject f
                WHERE f.user.email = :email
                  AND (
                      :keyword IS NULL
                      OR LOWER(f.subject.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                      OR LOWER(f.subject.code) LIKE LOWER(CONCAT('%', :keyword, '%'))
                  )
            """)
    Page<FavoriteSubject> findAllBySearchAndUserEmail(
            @Param("keyword") String keyword,
            @Param("email") String email,
            Pageable pageable
    );
}
