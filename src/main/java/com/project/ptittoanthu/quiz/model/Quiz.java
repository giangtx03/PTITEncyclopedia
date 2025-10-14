package com.project.ptittoanthu.quiz.model;

import com.project.ptittoanthu.common.base.entity.BaseEntity;
import com.project.ptittoanthu.question.model.Question;
import com.project.ptittoanthu.subjects.model.Subject;
import com.project.ptittoanthu.users.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "quizzes")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Quiz extends BaseEntity {

    @Column(name = "number_of_question", nullable = false)
    int numberOfQuestion;

    @ManyToOne
    @JoinColumn(name = "create_by_id", referencedColumnName = "id")
    User createBy;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    Subject subject;

    @ManyToMany
    @JoinTable(
            name = "quiz_questions",
            joinColumns = @JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    List<Question> questions;
}
