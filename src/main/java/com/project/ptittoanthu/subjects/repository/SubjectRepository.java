package com.project.ptittoanthu.subjects.repository;

import com.project.ptittoanthu.subjects.model.Subject;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    boolean existsByCode(@NotBlank String code);

    @Override
    @Query("""
            SELECT s FROM Subject s WHERE :id = s.id
            """)
    Optional<Subject> findById(@Param("id") Integer id);

    @Query("""
            SELECT s FROM Subject s WHERE
            (:keyword IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) 
            OR LOWER(s.code) LIKE LOWER(CONCAT('%', :keyword, '%')))
            AND (:majorId IS NULL OR :majorId = s.major.id) 
            """)
    Page<Subject> findAllBySearchRequest(
            @Param("majorId") Integer majorId,
            @Param("keyword") String keyword, Pageable pageable);

    @Query("""
        SELECT s FROM Subject s
        JOIN s.favoriteSubjects fs
        WHERE fs.user.id = :userId
          AND (:keyword IS NULL OR
                LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(s.code) LIKE LOWER(CONCAT('%', :keyword, '%')))
          AND (:majorId IS NULL OR s.major.id = :majorId)
        """)
    Page<Subject> findAllBySearchRequestFavorite(
            @Param("majorId") Integer majorId,
            @Param("userId") Integer userId,
            @Param("keyword") String keyword,
            Pageable pageable);

    @Query("""
            SELECT s FROM Subject s
            JOIN s.users u
            WHERE u.id = :userId
              AND (
                    :keyword IS NULL
                    OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) 
                    OR LOWER(s.code) LIKE LOWER(CONCAT('%', :keyword, '%'))
              )
            """)
    Page<Subject> findAllByTeacherRequest(
            @Param("userId") Integer userId,
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
