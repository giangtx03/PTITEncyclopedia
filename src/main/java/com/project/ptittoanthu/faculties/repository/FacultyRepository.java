package com.project.ptittoanthu.faculties.repository;

import com.project.ptittoanthu.faculties.model.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
    boolean existsByCode(String code);

    @Override
    @Query("""
            SELECT f FROM Faculty f WHERE :id = f.id
            """)
    Optional<Faculty> findById(@Param("id") Integer id);

    @Query("""
            SELECT f FROM Faculty f WHERE
            (:keyword IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%')) 
                OR LOWER(f.code) LIKE LOWER(CONCAT('%', :keyword, '%')))
            AND
            (:id IS NULL OR f.id = :id)
            """)
    Page<Faculty> findAllBySearchRequest(@Param("id") Integer id, @Param("keyword") String keyword, Pageable pageable);
}
