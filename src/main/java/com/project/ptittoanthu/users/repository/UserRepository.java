package com.project.ptittoanthu.users.repository;

import com.project.ptittoanthu.users.model.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(@NotBlank(message = "valid.email.notBlank") String email);
}
