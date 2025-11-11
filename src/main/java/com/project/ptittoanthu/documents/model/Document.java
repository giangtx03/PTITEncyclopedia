package com.project.ptittoanthu.documents.model;

import com.project.ptittoanthu.common.base.entity.BaseEntity;
import com.project.ptittoanthu.review.model.Review;
import com.project.ptittoanthu.subjects.model.Subject;
import com.project.ptittoanthu.users.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
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

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "documents")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Document extends BaseEntity {

    @Column(name = "title", length = 255, nullable = false)
    String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    DocumentType type;

    @Column(name = "file_path")
    String filePath;

    @Column(name = "enable")
    boolean enable;

    @Column(name = "author")
    String author;
    
    @Column(name = "publish_time")
    LocalDate publishTime;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    User owner;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    Subject subject;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
    List<Review> reviews;
}
