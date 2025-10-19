package com.project.ptittoanthu.majors.repository;

import com.project.ptittoanthu.majors.model.Major;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MajorRepository extends JpaRepository<Major, Integer> {
    boolean existsByCode(String code);

    @Override
    @Query("""
            SELECT m FROM Major m WHERE :id = m.id
            """)
    Optional<Major> findById(@Param("id") Integer id);

    @Query("""
            SELECT m FROM Major m WHERE
            (:keyword IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) 
            OR LOWER(m.code) LIKE LOWER(CONCAT('%', :keyword, '%')))
            AND (:facultyId IS NULL OR :facultyId = m.faculty.id) 
            """)
    Page<Major> findAllBySearchRequest(
            @Param("facultyId") Integer facultyId,
            @Param("keyword") String keyword, Pageable pageable);
}
