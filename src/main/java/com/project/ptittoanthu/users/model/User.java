package com.project.ptittoanthu.users.model;

import com.project.ptittoanthu.common.base.entity.BaseEntity;
import com.project.ptittoanthu.documents.model.Document;
import com.project.ptittoanthu.favorite.model.Bookmark;
import com.project.ptittoanthu.favorite.model.FavoriteSubject;
import com.project.ptittoanthu.notify.model.Notification;
import com.project.ptittoanthu.question.model.Question;
import com.project.ptittoanthu.quiz.model.Quiz;
import com.project.ptittoanthu.quiz.model.QuizResult;
import com.project.ptittoanthu.review.model.Review;
import com.project.ptittoanthu.subjects.model.Subject;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class User extends BaseEntity implements UserDetails {

    @Column(name = "username")
    String username;
    @Column(name = "email", length = 64, nullable = false, unique = true)
    String email;
    @Column(name = "password")
    String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;
    @Column(name = "is_active", nullable = false)
    boolean active;
    @Column(name = "is_locked", nullable = false)
    boolean locked;
    @Column(name = "address")
    private String address;
    @Column(name = "dob")
    private LocalDate dob;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "avatar")
    private String avatar;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    List<Document> documents;

    @OneToMany(mappedBy = "createBy", fetch = FetchType.LAZY)
    List<Quiz> quizzes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<QuizResult> quizResults;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Question> questions;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "course_sections",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    Set<Subject> subjects = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Review> reviews;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Notification> notifications;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<FavoriteSubject> favoriteSubjects;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getAuthority()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
