package com.project.ptittoanthu.subjects.model;

import com.project.ptittoanthu.common.base.entity.BaseEntity;
import com.project.ptittoanthu.documents.model.Document;
import com.project.ptittoanthu.favorite.model.FavoriteSubject;
import com.project.ptittoanthu.majors.model.Major;
import com.project.ptittoanthu.question.model.Question;
import com.project.ptittoanthu.quiz.model.Quiz;
import com.project.ptittoanthu.users.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "subjects")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Subject extends BaseEntity {

    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "code", length = 64, nullable = false, unique = true)
    String code;

    @ManyToOne
    @JoinColumn(name = "major_id", referencedColumnName = "id")
    Major major;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Document> documents;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Question> questions;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Quiz> quizzes;

    @ManyToMany(mappedBy = "subjects", fetch = FetchType.LAZY)
    Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    List<FavoriteSubject> favoriteSubjects;
}
