package com.project.ptittoanthu.users.repository;

import com.project.ptittoanthu.users.model.Role;
import com.project.ptittoanthu.users.model.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Override
    @Query("""
            SELECT u FROM User u
            WHERE u.id = :id
            """)
    Optional<User> findById(@Param("id") Integer id);

    @Query("""
            SELECT u FROM User u
            WHERE u.email = :email
            """)
    Optional<User> findByEmail(@Param("email") String email);
    @Query("""
            SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
            FROM User u
            WHERE u.email = :email
        """)
    boolean existsByEmail(@Param("email") String email);

    @Query("""
            SELECT u FROM User u
            WHERE
                (
                    :keyword IS NULL
                    OR LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR LOWER(u.address) LIKE LOWER(CONCAT('%', :keyword, '%'))
                )
                AND (:active IS NULL OR u.active = :active)
                AND (:locked IS NULL OR u.locked = :locked)
                AND (:role IS NULL OR u.role = :role)
            """)
    Page<User> findAllBySearchRequest(
            @Param("keyword") String keyword,
            @Param("active") Boolean active,
            @Param("locked") Boolean locked,
            @Param("role") Role role,
            Pageable pageable
    );
}
