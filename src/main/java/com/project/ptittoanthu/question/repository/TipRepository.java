package com.project.ptittoanthu.question.repository;

import com.project.ptittoanthu.question.model.Tip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipRepository extends JpaRepository<Tip, Integer> {
    List<Tip> findAllByQuestionId(Integer questionId);
}
