package com.project.ptittoanthu.question.repository;

import com.project.ptittoanthu.question.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Integer> {
    List<Option> findAllByQuestionId(Integer questionId);
}
