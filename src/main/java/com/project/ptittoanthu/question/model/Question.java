package com.project.ptittoanthu.question.model;

import com.project.ptittoanthu.common.base.entity.BaseEntity;
import com.project.ptittoanthu.quiz.model.Quiz;
import com.project.ptittoanthu.quiz.model.QuizResultItem;
import com.project.ptittoanthu.subjects.model.Subject;
import com.project.ptittoanthu.users.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Question extends BaseEntity {
    @Column(name = "content", nullable = false)
    String content;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    Subject subject;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Option> options = new ArrayList<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Tip> tips = new ArrayList<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    List<QuizResultItem> quizResultItems;

    @ManyToMany(mappedBy = "questions", fetch = FetchType.LAZY)
    List<Quiz> quizzes;
}
